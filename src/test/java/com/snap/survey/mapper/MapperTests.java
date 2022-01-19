package com.snap.survey.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.snap.survey.entity.RoleEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.UserPrincipal;
import com.snap.survey.model.enums.Role;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTests {

  @Autowired UserPrincipalMapper userPrincipalMapper;

  @Test
  void userPrincipalMapperTest() {
    String email = "ali@mail.com";
    String password = "password";
    String username = "ali";
    UserEntity user =
        UserEntity.builder()
            .username(username)
            .password(password)
            .email(email)
            .roles(
                Set.of(
                    RoleEntity.builder().roleName(Role.ROLE_USER).build(),
                    RoleEntity.builder().roleName(Role.ROLE_ADMIN).build()))
            .build();
    UserPrincipal userPrincipal = userPrincipalMapper.toUserPrincipal(user);
    assertEquals(userPrincipal.getEmail(), email);
    assertEquals(userPrincipal.getPassword(), password);
    assertEquals(userPrincipal.getAuthorities().size(), 2);
  }
}
