package com.snap.survey.entity;

import com.snap.survey.model.enums.QuestionType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_type")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionTypeEntity {
  @Id private Long id;

  @Column(name = "name", updatable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private QuestionType typeName;

  @OneToMany(mappedBy = "questionType")
  private Set<QuestionEntity> questions = new HashSet<>();
}
