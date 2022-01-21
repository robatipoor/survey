package com.snap.survey.repository;

import com.snap.survey.entity.AnswerEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

  @Query(
      "SELECT a FROM AnswerEntity a INNER JOIN a.survey s "
          + "INNER JOIN s.user u WHERE a.id = :id AND u.user.id  = :userId")
  Optional<AnswerEntity> findByIdAndAdminUserId(Long id, Long userId);

  @Query(
      "SELECT a FROM AnswerEntity a INNER JOIN a.survey s "
          + "INNER JOIN s.user u WHERE s.slug = :slug AND u.user.id  = :userId")
  Page<AnswerEntity> findAllBySurveySlugAndAdminUserId(String slug, Long userId, Pageable page);

  @Query("SELECT COUNT(a) FROM AnswerEntity a GROUP BY a.user HAVING a.survey.slug = :surveySlug")
  int countAllUserAnswerBySurveySlug(String surveySlug);

  @Query(
      "SELECT COUNT(a) FROM AnswerEntity a GROUP BY a.user HAVING a.survey.slug = :surveySlug AND a.choice.id = :choiceId")
  int countAllUserAnswerByIdAndSurveySlug(String surveySlug, Long choiceId);
}
