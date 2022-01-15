package com.snap.survey.repository;

import com.snap.survey.entity.RoleEntity;
import com.snap.survey.model.enums.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByRoleName(Role role);
}
