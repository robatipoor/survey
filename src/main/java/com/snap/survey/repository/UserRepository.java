package com.snap.survey.repository;

import com.snap.survey.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsernameOrEmail(String username, String email);

  boolean existsByUsernameOrEmail(String username, String email);
}
