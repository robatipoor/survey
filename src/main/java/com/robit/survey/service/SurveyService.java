package com.robit.survey.service;

import com.robit.survey.entity.SurveyEntity;
import com.robit.survey.entity.UserEntity;
import com.robit.survey.model.request.CreateSurveyRequest;
import com.robit.survey.model.request.SubmitSurveyRequest;
import com.robit.survey.model.response.*;
import com.snap.survey.model.response.*;
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

  Page<AnswerResultResponse> getResultResponse(String surveySlug, Pageable page);

  void update(Long surveyId, Function<SurveyEntity, SurveyEntity> func);

  Page<QuestionResponse> readQuestion(String slug, Pageable page);
}
