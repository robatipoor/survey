package com.snap.survey.entity;

import java.util.Objects;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
public abstract class BaseEntity {
  // TODO for security reason we can use uuid
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public BaseEntity(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseEntity baseEntity = (BaseEntity) o;
    return id.equals(baseEntity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
