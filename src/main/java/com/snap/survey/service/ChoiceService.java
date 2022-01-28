package com.snap.survey.service;

import com.snap.survey.entity.ChoiceEntity;
import java.util.List;
import java.util.function.Function;

public interface ChoiceService {
  ChoiceEntity getByIdAndQuestionId(Long choiceId, Long questionId);

  boolean existsByIdAndQuestionId(Long choiceId, Long questionId);

  List<ChoiceEntity> getAllByQuestionId(Long questionId);

  Long save(ChoiceEntity choice);

  void update(Long choiceId, Function<ChoiceEntity, ChoiceEntity> func);
}
