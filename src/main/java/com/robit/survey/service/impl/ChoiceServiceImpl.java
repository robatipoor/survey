package com.robit.survey.service.impl;

import com.robit.survey.entity.ChoiceEntity;
import com.robit.survey.repository.ChoiceRepository;
import com.robit.survey.service.ChoiceService;
import com.robit.survey.util.AppExceptionUtil;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public Long save(ChoiceEntity choice) {
    try {
      var result = choiceRepository.save(choice);
      log.info("success save choiceId : {}", result.getId());
      return result.getId();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("save choice entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.entity.failed", e.getMessage());
    }
  }

  //  @Override
  @Transactional
  public void update(Long choiceId, Function<ChoiceEntity, ChoiceEntity> func) {
    choiceRepository
        .findById(choiceId)
        .map(func)
        .ifPresent(
            choice -> {
              this.save(choice);
              log.info("success update userId : {}", choiceId);
            });
  }
}
