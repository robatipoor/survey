package com.robit.survey.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "choices")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceEntity extends DateAuditEntity {

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "number", nullable = false)
  private Integer number;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private QuestionEntity question;
}
