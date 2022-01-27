package com.snap.survey.service.impl;

import com.snap.survey.entity.ChoiceEntity;
import com.snap.survey.repository.ChoiceRepository;
import com.snap.survey.service.ChoiceService;
import com.snap.survey.util.AppExceptionUtil;
import java.util.List;
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
        .orElseThrow(() -> appExceptionUtil.getBusinessException("find.entity.failed"));
  }

  @Override
  public List<ChoiceEntity> getAllByQuestionId(Long questionId) {
    return choiceRepository.findAllByQuestionId(questionId);
  }

  @Override
  public boolean existsByIdAndQuestionId(Long choiceId, Long questionId) {
    return choiceRepository.existsByIdAndQuestionId(choiceId, questionId);
  }

  @Override
  public void save(ChoiceEntity choice) {
    try {
      choiceRepository.save(choice);
    } catch (Exception e) {
      log.error("save choice entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.entity.failed", e.getMessage());
    }
  }
}
