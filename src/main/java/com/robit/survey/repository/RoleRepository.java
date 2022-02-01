package com.robit.survey.repository;

import com.robit.survey.entity.RoleEntity;
import com.robit.survey.model.enums.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByRoleName(Role role);
}
