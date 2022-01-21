package com.snap.survey.service;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.model.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
  Page<QuestionResponse> getPage(Long userId, String slug, Pageable page);

  QuestionResponse getOne(Long userId, Long questionId, Pageable page);

  void save(QuestionEntity question);

  QuestionEntity getByQuestionIdAndSurvey(Long questionId, SurveyEntity survey);

  boolean existsByQuestionIdAndSurvey(Long questionId, SurveyEntity survey);
}
