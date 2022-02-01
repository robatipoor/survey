package com.robit.survey.service;

import static org.junit.jupiter.api.Assertions.*;

import com.robit.survey.model.request.CreateChoiceRequest;
import com.robit.survey.model.request.CreateQuestionRequest;
import com.robit.survey.model.request.CreateSurveyRequest;
import com.robit.survey.repository.QuestionRepository;
import com.robit.survey.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class QuestionServiceTests {

  private final QuestionRepository questionRepository;
  private final QuestionService questionService;
  private final UserRepository userRepository;
  private final SurveyService surveyService;

  @Autowired
  public QuestionServiceTests(
      QuestionRepository questionRepository,
      QuestionService questionService,
      UserRepository userRepository,
      SurveyService surveyService) {
    this.questionRepository = questionRepository;
    this.questionService = questionService;
    this.userRepository = userRepository;
    this.surveyService = surveyService;
  }

  @Test
  public void createSurveyTest() {
    var users = userRepository.findAll();
    var request =
        new CreateSurveyRequest(
            "new survey",
            List.of(
                new CreateQuestionRequest(
                    "why ?", List.of(new CreateChoiceRequest(1, "option 1")))),
            1);
    var response = surveyService.create(users.get(0).getId(), request);
    assertEquals(
        questionService.getBySurveySlug(response.slug(), Pageable.ofSize(1)).getContent().size(),
        1);
  }
}
