package com.snap.survey.service;

import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyService {
  CreateSurveyResponse create(Long userId, CreateSurveyRequest request);

  Page<SurveyResponse> getPage(Long userId, Pageable page);

  ResultSurveyResponse getResult(Long userId, String slug, Pageable page);

  void submit(Long userId, String slug, SubmitSurveyRequest request);

  SurveyEntity createSurvey(String title, long expireDays, UserEntity user);

  SurveyEntity getBySlugAndUserId(String slug, Long userId);

  SurveyEntity getBySlug(String slug);

  void save(SurveyEntity survey);
}
