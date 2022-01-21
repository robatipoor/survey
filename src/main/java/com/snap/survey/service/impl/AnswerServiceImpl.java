package com.snap.survey.service.impl;

import com.snap.survey.entity.*;
import com.snap.survey.mapper.AnswerMapper;
import com.snap.survey.model.response.AnswerChoiceResultResponse;
import com.snap.survey.model.response.AnswerResponse;
import com.snap.survey.model.response.AnswerResultResponse;
import com.snap.survey.repository.AnswerRepository;
import com.snap.survey.service.*;
import com.snap.survey.util.AppExceptionUtil;
import java.util.List;
import java.util.stream.Collectors;
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
  private final UserService userService;
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
    this.userService = userService;
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
        .orElseThrow(() -> appExceptionUtil.getAppException("", ""));
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
  public void save(AnswerEntity answer) {
    try {
      answerRepository.save(answer);
    } catch (Exception e) {
      log.error("save answer entity exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException("", "");
    }
  }

  @Override
  @Transactional
  public Page<AnswerResultResponse> getResultResponse(String surveySlug, Pageable page) {
    var numberOfParticipants = getNumberOfParticipants(surveySlug);
    return questionService
        .getBySurveySlug(surveySlug, page)
        .map(
            question -> {
              List<AnswerChoiceResultResponse> answers =
                  choiceService.getAllByQuestionId(question.getId()).stream()
                      .map(
                          choice -> {
                            var percentage =
                                numberOfParticipants
                                    * 1.0
                                    / getNumberOfParticipantsChoice(surveySlug, choice.getId());
                            return new AnswerChoiceResultResponse(
                                choice.getNumber(), choice.getContent(), percentage);
                          })
                      .collect(Collectors.toList());
              return new AnswerResultResponse(question.getId(), question.getContent(), answers);
            });
  }
}
