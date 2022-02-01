package com.robit.survey.service.impl;

import com.robit.survey.entity.*;
import com.robit.survey.mapper.AnswerMapper;
import com.robit.survey.model.response.AnswerResponse;
import com.robit.survey.repository.AnswerRepository;
import com.robit.survey.service.AnswerService;
import com.robit.survey.service.ChoiceService;
import com.robit.survey.service.QuestionService;
import com.robit.survey.service.UserService;
import com.robit.survey.util.AppExceptionUtil;
import com.snap.survey.entity.*;
import com.snap.survey.service.*;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;
  private final ChoiceService choiceService;
  private final AppExceptionUtil appExceptionUtil;
  private final QuestionService questionService;

  public AnswerServiceImpl(
      AnswerRepository answerRepository,
      AnswerMapper answerMapper,
      ChoiceService choiceService,
      AppExceptionUtil appExceptionUtil,
      UserService userService,
      QuestionService questionService) {
    this.answerRepository = answerRepository;
    this.answerMapper = answerMapper;
    this.choiceService = choiceService;
    this.appExceptionUtil = appExceptionUtil;
    this.questionService = questionService;
  }

  @Override
  public Page<AnswerResponse> getPage(Long userId, String slug, Pageable page) {
    return answerRepository
        .findAllBySurveySlugAndAdminUserId(slug, userId, page)
        .map(answerMapper::toResponse);
  }

  @Override
  public AnswerResponse getOne(Long userId, Long answerId) {
    return answerRepository
        .findByIdAndAdminUserId(answerId, userId)
        .map(answerMapper::toResponse)
        .orElseThrow(() -> appExceptionUtil.getBusinessException("find.entity.failed"));
  }

  @Override
  public int getNumberOfParticipants(String surveySlug) {
    return answerRepository.countAllUserAnswerBySurveySlug(surveySlug);
  }

  @Override
  public int getNumberOfParticipantsChoice(String surveySlug, Long choiceId) {
    return answerRepository.countAllUserAnswerByIdAndSurveySlug(surveySlug, choiceId);
  }

  public AnswerEntity createAnswer(
      Long choiceId, Long questionId, SurveyEntity survey, UserEntity user) {
    ChoiceEntity choice = choiceService.getByIdAndQuestionId(choiceId, questionId);
    QuestionEntity question = questionService.getByQuestionIdAndSurvey(questionId, survey);
    AnswerEntity answer = new AnswerEntity();
    answer.setSurvey(survey);
    answer.setUser(user);
    answer.setChoice(choice);
    answer.setQuestion(question);
    return answer;
  }

  @Override
  @Transactional
  public Long save(AnswerEntity answer) {
    try {
      var result = answerRepository.save(answer);
      log.info("success save answerId : {}", result.getId());
      return result.getId();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("save answer entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.entity.failed", e.getMessage());
    }
  }

  // @Override
  @Transactional
  public void update(Long answerId, Function<AnswerEntity, AnswerEntity> func) {
    answerRepository
        .findById(answerId)
        .map(func)
        .ifPresent(
            answer -> {
              this.save(answer);
              log.info("success update answerId : {}", answerId);
            });
  }
}
