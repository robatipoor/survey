package com.snap.survey.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

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

  @NaturalId
  @Column(nullable = false, unique = true)
  private String slug;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(name = "expire_date", nullable = false)
  private Instant expireDate;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
  private Set<QuestionEntity> questions = new HashSet<>();
}
