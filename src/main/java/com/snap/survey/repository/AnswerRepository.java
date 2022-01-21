package com.snap.survey.repository;

import com.snap.survey.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

  @Query("SELECT COUNT(a) FROM AnswerEntity a GROUP BY a.user HAVING a.survey.slug = :surveySlug")
  int countAllUserAnswerBySurveySlug(String surveySlug);

  @Query(
      "SELECT COUNT(a) FROM AnswerEntity a GROUP BY a.user HAVING a.survey.slug = :surveySlug AND a.choice.id = :choiceId")
  int countAllUserAnswerByIdAndSurveySlug(String surveySlug, Long choiceId);
}
