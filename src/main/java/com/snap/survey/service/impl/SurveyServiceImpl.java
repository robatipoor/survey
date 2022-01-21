package com.snap.survey.service.impl;

import com.snap.survey.entity.*;
import com.snap.survey.mapper.AnswerMapper;
import com.snap.survey.mapper.QuestionMapper;
import com.snap.survey.mapper.SurveyMapper;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.repository.AnswerRepository;
import com.snap.survey.repository.SurveyRepository;
import com.snap.survey.service.ChoiceService;
import com.snap.survey.service.QuestionService;
import com.snap.survey.service.SurveyService;
import com.snap.survey.service.UserService;
import com.snap.survey.util.AppExceptionUtil;
import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

  private final SurveyRepository surveyRepository;
  private final QuestionMapper questionMapper;
  private final QuestionService questionService;
  private final ChoiceService choiceService;
  private final AppExceptionUtil appExceptionUtil;
  private final UserService userService;
  private final SurveyMapper surveyResponseMapper;
  private final AnswerMapper answerMapper;
  private final AnswerRepository answerRepository;

  public SurveyServiceImpl(
      SurveyRepository surveyRepository,
      QuestionMapper questionMapper,
      QuestionService questionService,
      ChoiceService choiceService,
      AppExceptionUtil appExceptionUtil,
      UserService userService,
      SurveyMapper surveyResponseMapper,
      AnswerMapper answerMapper,
      AnswerRepository answerRepository) {
    this.surveyRepository = surveyRepository;
    this.questionMapper = questionMapper;
    this.questionService = questionService;
    this.choiceService = choiceService;
    this.appExceptionUtil = appExceptionUtil;
    this.userService = userService;
    this.surveyResponseMapper = surveyResponseMapper;
    this.answerMapper = answerMapper;
    this.answerRepository = answerRepository;
  }

  @Transactional
  public CreateSurveyResponse create(Long userId, CreateSurveyRequest request) {
    UserEntity user = userService.getByUserId(userId);
    SurveyEntity survey = createSurveyEntity(request.title(), user);
    this.save(survey);
    request.questions().stream().map(questionMapper::toEntity).forEach(questionService::save);
    return new CreateSurveyResponse(survey.getSlug());
  }

  public SurveyEntity createSurveyEntity(String title, UserEntity user) {
    SurveyEntity survey = new SurveyEntity();
    survey.setTitle(title);
    survey.setUser(user);
    survey.setTitle(UUID.randomUUID().toString().replace("-", ""));
    return survey;
  }

  public Page<SurveyResponse> getPage(Long userId, Pageable page) {
    UserEntity user = userService.getByUserId(userId);
    return surveyRepository
        .findAllByUser(user, page)
        .map(
            surveyEntity -> {
              boolean isExpired = surveyEntity.getExpireDate().isBefore(Instant.now());
              return new SurveyResponse(surveyEntity.getTitle(), surveyEntity.getSlug(), isExpired);
            });
  }

  public ResultSurveyResponse getResult(Long userId, String slug, Pageable page) {
    UserEntity user = userService.getByUserId(userId);

    // TODO impl
    return null;
  }

  @Transactional
  @Override
  public void submit(Long userId, String slug, SubmitSurveyRequest request) {
    UserEntity user = userService.getByUserId(userId);
    SurveyEntity survey = getBySlug(slug);
    request.answers().stream()
        .map(answer -> this.createAnswer(answer.choiceId(), answer.questionId(), survey, user))
        .forEach(answerRepository::save);
  }

  public AnswerEntity createAnswer(
      Long choiceId, Long questionId, SurveyEntity survey, UserEntity user) {
    ChoiceEntity choice = choiceService.getByIdAndQuestionId(choiceId, questionId);
    QuestionEntity question = questionService.getByQuestionIdAndSurvey(questionId, survey);
    AnswerEntity answer = new AnswerEntity();
    answer.setSurvey(survey);
    answer.setUser(user);
    answer.setChoice(choice);
    answer.setQuestion(question);
    return answer;
  }

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
