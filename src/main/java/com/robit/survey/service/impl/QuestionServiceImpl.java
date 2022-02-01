package com.robit.survey.service.impl;

import com.robit.survey.entity.QuestionEntity;
import com.robit.survey.entity.SurveyEntity;
import com.robit.survey.mapper.QuestionMapper;
import com.robit.survey.model.response.QuestionResponse;
import com.robit.survey.repository.QuestionRepository;
import com.robit.survey.service.QuestionService;
import com.robit.survey.util.AppExceptionUtil;
import java.util.function.Function;
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
        .orElseThrow(() -> appExceptionUtil.getBusinessException("find.entity.failed"));
  }

  @Override
  @Transactional
  public QuestionEntity getByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository
        .findByIdAndSurvey(questionId, survey)
        .orElseThrow(() -> appExceptionUtil.getBusinessException("find.entity.failed"));
  }

  @Override
  public boolean existsByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository.existsByIdAndSurvey(questionId, survey);
  }

  @Override
  @Transactional
  public Long save(QuestionEntity question) {
    try {
      var result = questionRepository.save(question);
      log.info("success save questionId : {}", result.getId());
      return result.getId();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("save question failed exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.exception.error", e.getMessage());
    }
  }

  @Override
  @Transactional
  public void update(Long questionId, Function<QuestionEntity, QuestionEntity> func) {
    questionRepository
        .findById(questionId)
        .map(func)
        .ifPresent(
            question -> {
              this.save(question);
              log.info("success update questionId : {}", questionId);
            });
  }
}
