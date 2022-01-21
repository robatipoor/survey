package com.snap.survey.service.impl;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.repository.QuestionRepository;
import com.snap.survey.service.QuestionService;
import com.snap.survey.util.AppExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final AppExceptionUtil appExceptionUtil;

  public QuestionServiceImpl(
      QuestionRepository questionRepository, AppExceptionUtil appExceptionUtil) {
    this.questionRepository = questionRepository;
    this.appExceptionUtil = appExceptionUtil;
  }

  public Page<QuestionResponse> getPage(Long userId, String slug, Pageable page) {
    // TODO impl
    return null;
  }

  public QuestionResponse getOne(Long userId, Long questionId, Pageable page) {
    // TODO impl
    return null;
  }

  @Override
  public void save(QuestionEntity question) {
    try {
      questionRepository.save(question);
    } catch (Exception e) {
      log.error("save question entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException("", "");
    }
  }

  @Override
  public QuestionEntity getByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository
        .findByQuestionIdAndSurvey(questionId, survey)
        .orElseThrow(
            () -> {
              return appExceptionUtil.getAppException("", "");
            });
  }

  @Override
  public boolean existsByQuestionIdAndSurvey(Long questionId, SurveyEntity survey) {
    return questionRepository.existsByIdAndSurvey(questionId, survey);
  }
}
