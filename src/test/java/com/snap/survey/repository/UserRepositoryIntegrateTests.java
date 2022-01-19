package com.snap.survey.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.snap.survey.entity.UserEntity;
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

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;

  @Test
  @Transactional
  public void saveTest1() {
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
    user = userRepository.save(user);
    userRepository.flush();
    assertNotNull(user.getId());
    userRepository.findByUsernameOrEmail(username, null).orElseThrow();
  }
}
