package com.robit.survey.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.robit.survey.entity.UserEntity;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.repository.RoleRepository;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserPrincipalMapperTests {

  private final UserPrincipalMapper userPrincipalMapper;
  private final RoleRepository roleRepository;

  @Autowired
  public UserPrincipalMapperTests(
      UserPrincipalMapper userPrincipalMapper, RoleRepository roleRepository) {
    this.userPrincipalMapper = userPrincipalMapper;
    this.roleRepository = roleRepository;
  }

  @Test
  void userPrincipalMapperTest() {
    var email = "ali@mail.com";
    var password = "password";
    var username = "ali";
    var roles = new HashSet<>(roleRepository.findAll());
    UserEntity user =
        UserEntity.builder()
            .username(username)
            .password(password)
            .email(email)
            .roles(roles)
            .build();
    UserPrincipal userPrincipal = userPrincipalMapper.toUserPrincipal(user);
    assertEquals(userPrincipal.getEmail(), email);
    assertEquals(userPrincipal.getPassword(), password);
    assertEquals(userPrincipal.getAuthorities().size(), 2);
  }
}
