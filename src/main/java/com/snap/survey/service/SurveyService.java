package com.snap.survey.service;

import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyService {
  CreateSurveyResponse create(Long userId, CreateSurveyRequest request);

  Page<SurveyResponse> getPage(Long userId, Pageable page);

  ResultSurveyResponse getResult(Long userId, String slug, Pageable page);

  void submit(Long userId, String slug, SubmitSurveyRequest request);

  SurveyEntity createPartialSurvey(String title, long expireDays, UserEntity user);

  SurveyEntity getBySlugAndUserId(String slug, Long userId);

  SurveyEntity getBySlug(String slug);

  Long save(SurveyEntity survey);

  void update(Long surveyId, Function<SurveyEntity, SurveyEntity> func);

  Page<QuestionResponse> readQuestion(String slug, Pageable page);
}
