package com.snap.survey.service;

import static org.junit.jupiter.api.Assertions.*;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.entity.UserEntity;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class SurveyServiceTests {

  private final SurveyService surveyService;

  @Autowired
  public SurveyServiceTests(SurveyService surveyService) {
    this.surveyService = surveyService;
  }

  @Test
  public void createSurveyTest() {
    var title = "title";
    var user = UserEntity.builder().firstName("name").build();
    var questions = Set.of(QuestionEntity.builder().content("q1").build());
    var survey = surveyService.createSurvey(title, 1, questions, user);
    assertEquals(title, survey.getTitle());
    assertNotNull(survey.getSlug());
    assertNotNull(survey.getExpireDate());
  }
}
