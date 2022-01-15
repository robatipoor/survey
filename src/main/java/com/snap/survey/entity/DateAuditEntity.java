package com.snap.survey.entity;

import java.time.Instant;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public abstract class DateAuditEntity extends BaseEntity {

  @CreatedDate
  @Column(name = "create_at", updatable = false)
  private Instant createAt;

  @LastModifiedDate
  @Column(name = "update_at")
  private Instant updateAt;
}
