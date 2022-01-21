package com.snap.survey.repository;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.entity.SurveyEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
  boolean existsByIdAndSurvey(Long id, SurveyEntity survey);

  Optional<QuestionEntity> findByQuestionIdAndSurvey(Long id, SurveyEntity survey);
}
