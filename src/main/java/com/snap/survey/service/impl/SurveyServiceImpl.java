package com.snap.survey.service.impl;

import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

  public CreateSurveyResponse create(Long userId, CreateSurveyRequest request) {
    // TODO impl
    return null;
  }

  public Page<SurveyResponse> getPage(Long userId, Pageable page) {
    // TODO impl
    return null;
  }

  public ResultSurveyResponse getResult(Long userId, String slug, Pageable page) {
    // TODO impl
    return null;
  }

  public void submit(Long userId, String slug, SubmitSurveyRequest request) {
    // TODO impl
  }
}
