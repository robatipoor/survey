package com.snap.survey.service.impl;

import com.snap.survey.entity.*;
import com.snap.survey.mapper.ChoiceMapper;
import com.snap.survey.mapper.QuestionMapper;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.repository.SurveyRepository;
import com.snap.survey.service.*;
import com.snap.survey.util.AppExceptionUtil;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

  private final SurveyRepository surveyRepository;
  private final QuestionMapper questionMapper;
  private final ChoiceMapper choiceMapper;
  private final QuestionService questionService;
  private final ChoiceService choiceService;
  private final AppExceptionUtil appExceptionUtil;
  private final UserService userService;
  private final AnswerService answerService;

  public SurveyServiceImpl(
      SurveyRepository surveyRepository,
      QuestionMapper questionMapper,
      ChoiceMapper choiceMapper,
      QuestionService questionService,
      ChoiceService choiceService,
      AppExceptionUtil appExceptionUtil,
      UserService userService,
      AnswerService answerService) {
    this.surveyRepository = surveyRepository;
    this.questionMapper = questionMapper;
    this.choiceMapper = choiceMapper;
    this.questionService = questionService;
    this.choiceService = choiceService;
    this.appExceptionUtil = appExceptionUtil;
    this.userService = userService;
    this.answerService = answerService;
  }

  @Transactional
  @Override
  public CreateSurveyResponse create(Long userId, CreateSurveyRequest request) {
    var user = userService.getByUserId(userId);
    var survey = createPartialSurvey(request.title(), request.expireDays(), user);
    this.save(survey);
    request.questions().stream()
        .map(
            questionRequest -> {
              var question = questionMapper.toEntity(questionRequest);
              var choices = choiceMapper.toEntity(questionRequest.choicesRequest());
              question.setSurvey(survey);
              return Pair.of(question, choices);
            })
        .forEach(
            pair -> {
              var question = pair.getFirst();
              var choices = pair.getSecond();
              var result = questionService.save(question);
              log.info("success create questionId : {}", result);
              choices.stream()
                  .peek(choice -> choice.setQuestion(question))
                  .forEach(choiceService::save);
            });
    return new CreateSurveyResponse(survey.getSlug());
  }

  @Override
  public SurveyEntity createPartialSurvey(String title, long expireDays, UserEntity user) {
    var survey = new SurveyEntity();
    survey.setTitle(title);
    survey.setExpireDate(Instant.now().plus(expireDays, ChronoUnit.DAYS));
    survey.setUser(user);
    survey.setSlug(UUID.randomUUID().toString().replace("-", ""));
    return survey;
  }

  @Override
  public Page<SurveyResponse> getPage(Long userId, Pageable page) {
    var user = userService.getByUserId(userId);
    return surveyRepository
        .findAllByUser(user, page)
        .map(
            surveyEntity -> {
              boolean isExpired = this.isExpire(surveyEntity.getExpireDate());
              return new SurveyResponse(surveyEntity.getTitle(), surveyEntity.getSlug(), isExpired);
            });
  }

  @Override
  public ResultSurveyResponse getResult(Long userId, String surveySlug, Pageable page) {
    var survey = getBySlugAndUserId(surveySlug, userId);
    var isExpired = this.isExpire(survey.getExpireDate());
    var numberOfParticipants = answerService.getNumberOfParticipants(surveySlug);
    var results = answerService.getResultResponse(survey.getSlug(), page);
    return new ResultSurveyResponse(survey.getTitle(), isExpired, results, numberOfParticipants);
  }

  @Transactional
  @Override
  public void submit(Long userId, String slug, SubmitSurveyRequest request) {
    var user = userService.getByUserId(userId);
    var survey = getBySlug(slug);
    log.info("submit survey userId : {} surveyId : {}", userId, survey.getId());
    if (this.isExpire(survey.getExpireDate())) {
      log.debug("survey is expired : {} ", slug);
      throw appExceptionUtil.getBusinessException("survey.expire.error");
    }
    if (request.answers().size() != questionService.countBySurveySlug(slug)) {
      log.debug("invalid request submitted by userId : {} request : {}", userId, request);
      throw appExceptionUtil.getBusinessException("invalid.input.error");
    }
    // TODO if user submit survey more than one throws exception
    request.answers().stream()
        .map(
            answer ->
                answerService.createAnswer(answer.choiceId(), answer.questionId(), survey, user))
        .forEach(answerService::save);
  }

  public boolean isExpire(Instant expireDate) {
    return expireDate.isBefore(Instant.now());
  }

  @Override
  public SurveyEntity getBySlugAndUserId(String slug, Long userId) {
    return surveyRepository
        .findBySlugAndUserId(slug, userId)
        .orElseThrow(() -> appExceptionUtil.getBusinessException("survey.not.found.error"));
  }

  @Override
  public SurveyEntity getBySlug(String slug) {
    return surveyRepository
        .findBySlug(slug)
        .orElseThrow(() -> appExceptionUtil.getBusinessException("survey.not.found.error"));
  }

  @Override
  @Transactional
  public Long save(SurveyEntity survey) {
    try {
      var result = surveyRepository.save(survey);
      log.info("success save surveyId : {}", result.getId());
      return result.getId();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("save survey failed exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.exception.error", e.getMessage());
    }
  }

  @Override
  @Transactional
  public void update(Long surveyId, Function<SurveyEntity, SurveyEntity> func) {
    surveyRepository
        .findById(surveyId)
        .map(func)
        .ifPresent(
            survey -> {
              this.save(survey);
              log.info("success update surveyId : {}", survey);
            });
  }

  @Override
  public Page<QuestionResponse> readQuestion(String slug, Pageable page) {
    var survey =
        surveyRepository
            .findBySlug(slug)
            .orElseThrow(() -> appExceptionUtil.getBusinessException("survey.not.found.error"));
    if (this.isExpire(survey.getExpireDate())) {
      throw appExceptionUtil.getBusinessException("survey.expire.error");
    }
    return questionService.getBySurveySlug(slug, page);
  }
}
