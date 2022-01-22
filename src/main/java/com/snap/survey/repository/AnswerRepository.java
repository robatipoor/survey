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
          + "INNER JOIN s.user u WHERE a.id = :id AND u.id  = :userId")
  Optional<AnswerEntity> findByIdAndAdminUserId(Long id, Long userId);

  @Query(
      "SELECT a FROM AnswerEntity a INNER JOIN a.survey s "
          + "INNER JOIN s.user u WHERE s.slug = :slug AND u.id  = :userId")
  Page<AnswerEntity> findAllBySurveySlugAndAdminUserId(String slug, Long userId, Pageable page);

  @Query(
      value =
          "SELECT COUNT(1) FROM ( "
              + "SELECT s.slug,COUNT(s) FROM answers a INNER JOIN surveys s ON a.survey_id = s.id "
              + "GROUP BY a.user_id,s.slug HAVING s.slug = :surveySlug) r ",
      nativeQuery = true)
  int countAllUserAnswerBySurveySlug(String surveySlug);

  @Query(
      value =
          "SELECT COUNT(1) FROM ( "
              + "SELECT COUNT(s) co FROM answers a INNER JOIN surveys s ON a.survey_id = s.id "
              + "GROUP BY a.user_id,s.slug,a.choice_id HAVING s.slug = :surveySlug AND a.choice_id = :choiceId) r ",
      nativeQuery = true)
  int countAllUserAnswerByIdAndSurveySlug(String surveySlug, Long choiceId);
}
