package com.snap.survey.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "surveys")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyEntity extends DateAuditEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
  private Set<QuestionEntity> questions = new HashSet<>();
}
