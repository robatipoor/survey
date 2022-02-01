package com.robit.survey.repository;

import com.robit.survey.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsernameOrEmail(String username, String email);

  boolean existsByUsernameOrEmail(String username, String email);
}
