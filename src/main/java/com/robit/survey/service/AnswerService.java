package com.robit.survey.service;

import com.robit.survey.entity.AnswerEntity;
import com.robit.survey.entity.SurveyEntity;
import com.robit.survey.entity.UserEntity;
import com.robit.survey.model.response.AnswerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {
  Page<AnswerResponse> getPage(Long userId, String slug, Pageable page);

  AnswerResponse getOne(Long userId, Long answerId);

  int getNumberOfParticipants(String surveySlug);

  int getNumberOfParticipantsChoice(String surveySlug, Long choiceId);

  AnswerEntity createAnswer(Long choiceId, Long questionId, SurveyEntity survey, UserEntity user);

  Long save(AnswerEntity answer);
}
