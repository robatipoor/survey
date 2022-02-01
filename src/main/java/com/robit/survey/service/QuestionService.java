package com.robit.survey.service;

import com.robit.survey.entity.QuestionEntity;
import com.robit.survey.entity.SurveyEntity;
import com.robit.survey.model.response.QuestionResponse;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

  long countBySurveySlug(String slug);

  Page<QuestionResponse> getByUserIdAndSurveySlug(Long userId, String slug, Pageable page);

  QuestionResponse getOne(Long userId, Long questionId);

  Long save(QuestionEntity question);

  void update(Long questionId, Function<QuestionEntity, QuestionEntity> func);

  QuestionEntity getByQuestionIdAndSurvey(Long questionId, SurveyEntity survey);

  boolean existsByQuestionIdAndSurvey(Long questionId, SurveyEntity survey);

  Page<QuestionEntity> getEntityBySurveySlug(String surveySlug, Pageable page);

  Page<QuestionResponse> getBySurveySlug(String slug, Pageable page);
}
