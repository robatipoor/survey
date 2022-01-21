package com.snap.survey.service.impl;

import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.mapper.QuestionMapper;
import com.snap.survey.mapper.SurveyMapper;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.repository.QuestionRepository;
import com.snap.survey.repository.SurveyRepository;
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
  private final QuestionRepository questionRepository;
  private final AppExceptionUtil appExceptionUtil;
  private final UserService userService;
  private final SurveyMapper surveyResponseMapper;

  public SurveyServiceImpl(
      SurveyRepository surveyRepository,
      QuestionMapper questionMapper,
      QuestionRepository questionRepository,
      AppExceptionUtil appExceptionUtil,
      UserService userService,
      SurveyMapper surveyResponseMapper) {
    this.surveyRepository = surveyRepository;
    this.questionMapper = questionMapper;
    this.questionRepository = questionRepository;
    this.appExceptionUtil = appExceptionUtil;
    this.userService = userService;
    this.surveyResponseMapper = surveyResponseMapper;
  }

  @Transactional
  public CreateSurveyResponse create(Long userId, CreateSurveyRequest request) {
    UserEntity user = userService.getByUserId(userId);
    SurveyEntity survey = createSurveyEntity(request.title(), user);
    surveyRepository.save(survey);
    request.questions().stream().map(questionMapper::toEntity).forEach(questionRepository::save);
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

  public void submit(Long userId, String slug, SubmitSurveyRequest request) {
    UserEntity user = userService.getByUserId(userId);
    // TODO impl
  }
}
