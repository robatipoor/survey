package com.robit.survey.entity;

import com.robit.survey.model.enums.Role;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity extends BaseEntity {

  @Column(name = "name", updatable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private Role roleName;

  @ManyToMany(mappedBy = "roles")
  private Set<UserEntity> users = new HashSet<>();
}
