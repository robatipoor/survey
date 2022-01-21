package com.snap.survey.service;

import com.snap.survey.entity.ChoiceEntity;

public interface ChoiceService {
  ChoiceEntity getByIdAndQuestionId(Long choiceId, Long questionId);

  boolean existsByIdAndQuestionId(Long choiceId, Long questionId);
}
