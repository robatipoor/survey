package com.robit.survey.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.robit.survey.entity.UserEntity;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class UserRepositoryIntegrateTests {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserRepositoryIntegrateTests(
      UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Test
  @Transactional
  public void saveIntegrateTest() {
    String email = "ali@mail.com";
    String password = "password";
    String username = "ali";
    UserEntity user =
        UserEntity.builder()
            .firstName("fist_name")
            .lastName("last_name")
            .username(username)
            .password(password)
            .email(email)
            .roles(new HashSet<>(roleRepository.findAll()))
            .build();
    userRepository.save(user);
    userRepository.flush();
    assertNotNull(user.getId());
    userRepository.findByUsernameOrEmail(username, null).orElseThrow();
  }
}
