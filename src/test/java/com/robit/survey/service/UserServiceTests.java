package com.robit.survey.service;

import static org.junit.jupiter.api.Assertions.*;

import com.robit.survey.config.Constants;
import com.robit.survey.entity.UserEntity;
import com.robit.survey.mapper.UserPrincipalMapper;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.model.request.LoginRequest;
import com.robit.survey.model.request.RegisterRequest;
import com.robit.survey.model.response.LoginResponse;
import com.robit.survey.repository.UserRepository;
import com.robit.survey.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class UserServiceTests {

  private final UserService userService;
  private final UserRepository userRepository;
  private final UserPrincipalMapper userPrincipalMapper;
  private final TokenUtil tokenUtil;
  private final PasswordEncoder passwordEncoder;
  RegisterRequest registerRequest =
      new RegisterRequest("first_name", "last_name", "username", "email", "password");

  @Autowired
  public UserServiceTests(
      UserService userService,
      UserRepository userRepository,
      UserPrincipalMapper userPrincipalMapper,
      TokenUtil tokenUtil,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.userPrincipalMapper = userPrincipalMapper;
    this.tokenUtil = tokenUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Test
  public void createUserEntityFromRequestIntegrateTest() {
    UserEntity user = userService.createUserEntityFromRequest(registerRequest);
    assertEquals(user.getEmail(), "email");
    assertEquals(user.getUsername(), "username");
    assertEquals(user.getLastName(), "last_name");
    assertEquals(user.getFirstName(), "first_name");
    assertTrue(passwordEncoder.matches("password", user.getPassword()));
  }

  @Test
  @Transactional
  public void loginIntegrateTest() {
    LoginRequest loginRequest = new LoginRequest("user", "user");
    UserEntity user = userRepository.findByUsernameOrEmail("user", "user").orElseThrow();
    UserPrincipal userPrincipal = userPrincipalMapper.toUserPrincipal(user);
    LoginResponse loginResponse = userService.loginUser(loginRequest);
    assertEquals(loginResponse.tokenType(), Constants.BEARER);
    assertEquals(loginResponse.accessToken(), tokenUtil.generateToken(userPrincipal));
  }

  @Test
  @Transactional
  public void registerAndLoginIntegrateTest() {
    RegisterRequest registerRequest =
        new RegisterRequest("first", "last", "username", "user-mail@email.com", "pass");
    userService.registerUser(registerRequest);
    UserEntity user =
        userRepository
            .findByUsernameOrEmail(registerRequest.email(), registerRequest.email())
            .orElseThrow();
    UserPrincipal userPrincipal = userPrincipalMapper.toUserPrincipal(user);
    LoginRequest loginRequest =
        new LoginRequest(registerRequest.username(), registerRequest.password());
    LoginResponse loginResponse = userService.loginUser(loginRequest);
    assertEquals(loginResponse.tokenType(), Constants.BEARER);
    assertEquals(loginResponse.accessToken(), tokenUtil.generateToken(userPrincipal));
  }
}
