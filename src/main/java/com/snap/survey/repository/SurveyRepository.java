package com.snap.survey.repository;

import com.snap.survey.entity.SurveyEntity;
import com.snap.survey.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

  Page<SurveyEntity> findAllByUser(UserEntity user, Pageable page);

  Page<SurveyEntity> findAllByUserAndSlug(UserEntity user, String slug, Pageable page);
}
