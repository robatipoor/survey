package com.robit.survey.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "answers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "question_id"}))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerEntity extends DateAuditEntity {

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "choice_id")
  private ChoiceEntity choice;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private QuestionEntity question;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private SurveyEntity survey;
}
