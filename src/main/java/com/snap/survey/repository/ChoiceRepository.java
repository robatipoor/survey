package com.snap.survey.repository;

import com.snap.survey.entity.ChoiceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Long> {

  Optional<ChoiceEntity> findByIdAndQuestionId(Long id, Long questionId);

  List<ChoiceEntity> findAllByQuestionId(Long questionId);

  boolean existsByIdAndQuestionId(Long id, Long questionId);
}
