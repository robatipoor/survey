package com.snap.survey.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.snap.survey.entity.UserEntity;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class UserRepositoryTests {

  @MockBean private UserRepository userRepository;

  @BeforeEach
  public void setup() {
    UserEntity user = new UserEntity();
    user.setUsername("username");
    user.setEmail("email");
    user.setPassword("password");
    user.setActivated(true);
    userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByUsernameOrEmail(user.getUsername(), null))
        .thenReturn(Optional.of(user));
    Mockito.when(userRepository.findByUsernameOrEmail(user.getUsername(), user.getUsername()))
        .thenReturn(Optional.of(user));
    Mockito.when(userRepository.findByUsernameOrEmail(user.getEmail(), user.getUsername()))
        .thenReturn(Optional.of(user));
    Mockito.when(userRepository.findByUsernameOrEmail(user.getEmail(), user.getEmail()))
        .thenReturn(Optional.of(user));
    Mockito.when(userRepository.findByUsernameOrEmail(null, user.getEmail()))
        .thenReturn(Optional.of(user));
  }

  @Test
  public void findByUsernameOrEmailTest1() {
    var user = userRepository.findByUsernameOrEmail("username", null).orElseThrow();
    assertEquals(user.getEmail(), "email");
  }

  @Test
  public void findByUsernameOrEmailTest2() {
    var user = userRepository.findByUsernameOrEmail("email", "email");
    assertTrue(user.isPresent());
  }

  @Test
  public void findByUsernameOrEmailTest3() {
    var user = userRepository.findByUsernameOrEmail(null, null);
    assertFalse(user.isPresent());
  }
}
