package com.snap.survey.service;

import com.snap.survey.entity.AnswerEntity;
import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.response.AnswerResponse;
import com.snap.survey.model.response.AnswerResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {
  Page<AnswerResponse> getPage(Long userId, String slug, Pageable page);

  AnswerResponse getOne(Long userId, Long answerId);

  int getNumberOfParticipants(String surveySlug);

  int getNumberOfParticipantsChoice(String surveySlug, Long choiceId);

  AnswerEntity createAnswer(Long choiceId, Long questionId, SurveyEntity survey, UserEntity user);

  Long save(AnswerEntity answer);

  Page<AnswerResultResponse> getResultResponse(String surveySlug, Pageable page);
}
