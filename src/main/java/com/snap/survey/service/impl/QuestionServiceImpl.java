package com.snap.survey.service.impl;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.mapper.QuestionMapper;
import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.repository.QuestionRepository;
import com.snap.survey.service.QuestionService;
import com.snap.survey.util.AppExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final AppExceptionUtil appExceptionUtil;

  public QuestionServiceImpl(
      QuestionRepository questionRepository,
      QuestionMapper questionMapper,
      AppExceptionUtil appExceptionUtil) {
    this.questionRepository = questionRepository;
    this.questionMapper = questionMapper;
    this.appExceptionUtil = appExceptionUtil;
  }

  @Override
  public long countBySurveySlug(String slug) {
    return questionRepository.countBySurveySlug(slug);
  }

  @Override
  @Transactional
  public Page<QuestionResponse> getBySurveySlug(String slug, Pageable page) {
    return this.getEntityBySurveySlug(slug, page).map(questionMapper::toResponse);
  }

  @Override
  @Transactional
  public Page<QuestionEntity> getEntityBySurveySlug(String surveySlug, Pageable page) {
    return questionRepository.findAllBySurveySlug(surveySlug, page);
  }

  @Override
  @Transactional
  public Page<QuestionResponse> getByUserIdAndSurveySlug(Long userId, String slug, Pageable page) {
    return questionRepository
        .findAllBySurveySlugAndAdminUserId(slug, userId, page)
        .map(questionMapper::toResponse);
  }

  @Override
  @Transactional
  public QuestionResponse getOne(Long userId, Long questionId) {
    return questionRepository
        .findByIdAndAdminUserId(questionId, userId)
        .map(questionMapper::toResponse)
        .orElseThrow(
            () ->
                appExceptionUtil.getAppException(
                    "find.entity.failed.message", "find.entity.failed.code"));
  }

  @Override
  @Transactional
  public void save(QuestionEntity question) {
    try {
      questionRepository.save(question);
    } catch (Exception e) {
      log.error("save question entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException(
          "save.entity.failed.message", "save.entity.failed.message");
    }
  }

  @Override
  @Transactional
  public QuestionEntity getByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository
        .findByIdAndSurvey(questionId, survey)
        .orElseThrow(
            () -> {
              return appExceptionUtil.getAppException(
                  "find.entity.failed.message", "find.entity.failed.code");
            });
  }

  @Override
  public boolean existsByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository.existsByIdAndSurvey(questionId, survey);
  }
}
