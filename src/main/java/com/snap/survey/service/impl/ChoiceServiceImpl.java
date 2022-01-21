package com.snap.survey.service.impl;

import com.snap.survey.entity.ChoiceEntity;
import com.snap.survey.repository.ChoiceRepository;
import com.snap.survey.service.ChoiceService;
import com.snap.survey.util.AppExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChoiceServiceImpl implements ChoiceService {

  private final ChoiceRepository choiceRepository;
  private final AppExceptionUtil appExceptionUtil;

  public ChoiceServiceImpl(ChoiceRepository choiceRepository, AppExceptionUtil appExceptionUtil) {
    this.choiceRepository = choiceRepository;
    this.appExceptionUtil = appExceptionUtil;
  }

  @Override
  public ChoiceEntity getByIdAndQuestionId(Long choiceId, Long questionId) {
    return choiceRepository
        .findByIdAndQuestionId(choiceId, questionId)
        .orElseThrow(
            () -> {
              return appExceptionUtil.getAppException("", "");
            });
  }

  @Override
  public boolean existsByIdAndQuestionId(Long choiceId, Long questionId) {
    return choiceRepository.existsByIdAndQuestionId(choiceId, questionId);
  }
}
