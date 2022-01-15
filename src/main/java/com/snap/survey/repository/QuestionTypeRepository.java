package com.snap.survey.repository;

import com.snap.survey.entity.QuestionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionTypeEntity, Long> {}
