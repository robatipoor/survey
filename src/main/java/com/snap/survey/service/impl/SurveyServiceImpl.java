package com.snap.survey.service.impl;

import com.snap.survey.entity.*;
import com.snap.survey.mapper.AnswerMapper;
import com.snap.survey.mapper.ChoiceMapper;
import com.snap.survey.mapper.QuestionMapper;
import com.snap.survey.mapper.SurveyMapper;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.repository.SurveyRepository;
import com.snap.survey.service.*;
import com.snap.survey.util.AppExceptionUtil;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
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
  private final SurveyMapper surveyResponseMapper;
  private final AnswerMapper answerMapper;
  private final AnswerService answerService;

  public SurveyServiceImpl(
      SurveyRepository surveyRepository,
      QuestionMapper questionMapper,
      ChoiceMapper choiceMapper,
      QuestionService questionService,
      ChoiceService choiceService,
      AppExceptionUtil appExceptionUtil,
      UserService userService,
      SurveyMapper surveyResponseMapper,
      AnswerMapper answerMapper,
      AnswerService answerService) {
    this.surveyRepository = surveyRepository;
    this.questionMapper = questionMapper;
    this.choiceMapper = choiceMapper;
    this.questionService = questionService;
    this.choiceService = choiceService;
    this.appExceptionUtil = appExceptionUtil;
    this.userService = userService;
    this.surveyResponseMapper = surveyResponseMapper;
    this.answerMapper = answerMapper;
    this.answerService = answerService;
  }

  @Transactional
  @Override
  public CreateSurveyResponse create(Long userId, CreateSurveyRequest request) {
    var user = userService.getByUserId(userId);
    var survey = createSurvey(request.title(), request.expireDays(), user);
    this.save(survey);
    request.questions().stream()
        .map(
            questionRequest -> {
              var question = questionMapper.toEntity(questionRequest);
              var choices = choiceMapper.toEntity(questionRequest.choices());
              question.setSurvey(survey);
              return Pair.of(question, choices);
            })
        .forEach(
            pair -> {
              var question = pair.getFirst();
              var choices = pair.getSecond();
              questionService.save(question);
              choices.stream()
                  .peek(choice -> choice.setQuestion(question))
                  .forEach(choiceService::save);
            });
    return new CreateSurveyResponse(survey.getSlug());
  }

  @Override
  public SurveyEntity createSurvey(String title, long expireDays, UserEntity user) {
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
              boolean isExpired = surveyEntity.getExpireDate().isBefore(Instant.now());
              return new SurveyResponse(surveyEntity.getTitle(), surveyEntity.getSlug(), isExpired);
            });
  }

  @Override
  public ResultSurveyResponse getResult(Long userId, String surveySlug, Pageable page) {
    var survey = getBySlugAndUserId(surveySlug, userId);
    var isFinished = survey.getExpireDate().isBefore(Instant.now());
    var numberOfParticipants = answerService.getNumberOfParticipants(surveySlug);
    var results = answerService.getResultResponse(survey.getSlug(), page);
    return new ResultSurveyResponse(survey.getTitle(), isFinished, results, numberOfParticipants);
  }

  @Transactional
  @Override
  public void submit(Long userId, String slug, SubmitSurveyRequest request) {
    var user = userService.getByUserId(userId);
    var survey = getBySlug(slug);
    log.info(">>>>> submit survey userId : {} surveyId : {}", userId, survey.getId());
    request.answers().stream()
        .map(
            answer ->
                answerService.createAnswer(answer.choiceId(), answer.questionId(), survey, user))
        .forEach(answerService::save);
  }

  @Override
  public SurveyEntity getBySlugAndUserId(String slug, Long userId) {
    return surveyRepository
        .findBySlugAndUserId(slug, userId)
        .orElseThrow(
            () ->
                appExceptionUtil.getAppException(
                    "user.not.found.error.message", "user.not.found.error.code"));
  }

  @Override
  public SurveyEntity getBySlug(String slug) {
    return surveyRepository
        .findBySlug(slug)
        .orElseThrow(
            () ->
                appExceptionUtil.getAppException(
                    "user.not.found.error.message", "user.not.found.error.code"));
  }

  @Override
  public void save(SurveyEntity survey) {
    try {
      surveyRepository.save(survey);
    } catch (Exception e) {
      log.error("save survey entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException("", "");
    }
  }
}
