package com.robit.survey.repository;

import com.robit.survey.entity.QuestionEntity;
import com.robit.survey.entity.SurveyEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

  @Query("SELECT COUNT(1) FROM QuestionEntity q INNER JOIN q.survey s WHERE s.slug = :slug")
  long countBySurveySlug(String slug);

  boolean existsByIdAndSurvey(Long id, SurveyEntity survey);

  Optional<QuestionEntity> findByIdAndSurvey(Long id, SurveyEntity survey);

  @Query(
      "SELECT q FROM QuestionEntity q INNER JOIN q.survey s "
          + "INNER JOIN s.user u WHERE q.id = :id AND u.id = :userId")
  Optional<QuestionEntity> findByIdAndAdminUserId(Long id, Long userId);

  @Query(
      "SELECT q FROM QuestionEntity q INNER JOIN q.survey s "
          + "INNER JOIN s.user u WHERE s.slug = :slug AND u.id = :userId")
  Page<QuestionEntity> findAllBySurveySlugAndAdminUserId(String slug, Long userId, Pageable page);

  @Query("SELECT q FROM QuestionEntity q INNER JOIN q.survey s WHERE s.slug = :slug")
  Page<QuestionEntity> findAllBySurveySlug(String slug, Pageable page);
}
