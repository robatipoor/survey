package com.snap.survey.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionEntity extends DateAuditEntity {

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "survey_id")
  private SurveyEntity survey;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private Set<ChoiceEntity> choices = new HashSet<>();
}
