package com.snap.survey;

import com.snap.survey.repository.UserRepository;
import com.snap.survey.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@ActiveProfiles("test")
public class UserServiceTests {

  @Autowired UserRepository userRepository;
  @Autowired UserService userService;

  @Test
  public void test1() {}
}
