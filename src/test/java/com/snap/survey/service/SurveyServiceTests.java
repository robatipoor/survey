package com.snap.survey.service;

import static org.junit.jupiter.api.Assertions.*;

import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.*;
import com.snap.survey.repository.AnswerRepository;
import com.snap.survey.repository.ChoiceRepository;
import com.snap.survey.repository.QuestionRepository;
import com.snap.survey.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class SurveyServiceTests {

  private final SurveyService surveyService;
  private final UserRepository userRepository;
  private final QuestionRepository questionRepository;
  private final ChoiceRepository choiceRepository;
  private final AnswerRepository answerRepository;
  private final AnswerService answerService;

  @Autowired
  public SurveyServiceTests(
      SurveyService surveyService,
      UserRepository userRepository,
      QuestionRepository questionRepository,
      ChoiceRepository choiceRepository,
      AnswerRepository answerRepository,
      AnswerService answerService) {
    this.surveyService = surveyService;
    this.userRepository = userRepository;
    this.questionRepository = questionRepository;
    this.choiceRepository = choiceRepository;
    this.answerRepository = answerRepository;
    this.answerService = answerService;
  }

  @Test
  @Transactional
  public void surveyIntegrateTest() {
    var users = userRepository.findAll();
    var createQuestionRequests =
        List.of(
            new CreateQuestionRequest("why ?", List.of(new CreateChoiceRequest(1, "option 1"))));
    var createReq = new CreateSurveyRequest("new survey", createQuestionRequests, 1);
    var userId = users.get(0).getId();
    var response = surveyService.create(userId, createReq);
    log.info("generate slug : {}", response.slug());
    var questionEntities =
        questionRepository
            .findAllBySurveySlugAndAdminUserId(
                response.slug(), userId, Pageable.ofSize(createQuestionRequests.size()))
            .getContent();
    var submitAnswerRequests =
        questionEntities.stream()
            .map(
                question -> {
                  var choices = choiceRepository.findAllByQuestionId(question.getId());
                  return new SubmitAnswerRequest(question.getId(), choices.get(0).getId());
                })
            .collect(Collectors.toList());
    var submitReq = new SubmitSurveyRequest(submitAnswerRequests);
    surveyService.submit(users.get(0).getId(), response.slug(), submitReq);
    assertEquals(answerService.getNumberOfParticipants(response.slug()), 1);
  }

  @Test
  public void createTest() {
    var users = userRepository.findAll();
    var request =
        new CreateSurveyRequest(
            "new survey",
            List.of(
                new CreateQuestionRequest(
                    "why ?", List.of(new CreateChoiceRequest(1, "option 1")))),
            1);
    var response = surveyService.create(users.get(0).getId(), request);
    log.info("generate slug : {}", response.slug());
    assertNotNull(response.slug());
    assertNotNull(surveyService.getBySlug(response.slug()).getId());
    assertNotNull(surveyService.getBySlugAndUserId(response.slug(), users.get(0).getId()).getId());
  }

  @Test
  public void createSurveyTest() {
    var title = "title";
    var firstName = "fistName";
    var user = UserEntity.builder().firstName(firstName).build();
    var survey = surveyService.createSurvey(title, 1, user);
    assertEquals(title, survey.getTitle());
    assertEquals(survey.getUser().getFirstName(), firstName);
    assertNotNull(survey.getSlug());
    assertNotNull(survey.getExpireDate());
  }
}
