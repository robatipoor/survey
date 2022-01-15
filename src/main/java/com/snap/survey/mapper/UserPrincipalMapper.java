package com.snap.survey.mapper;

import com.snap.survey.entity.RoleEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper(componentModel = "spring")
public abstract class UserPrincipalMapper {

  @Mapping(source = "roles", target = "authorities", qualifiedByName = "rolesToAuthorities")
  public abstract UserPrincipal toUserPrincipal(UserEntity entity);

  @Named("rolesToAuthorities")
  public Collection<? extends GrantedAuthority> convert(Set<RoleEntity> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
        .collect(Collectors.toList());
  }

  public List<String> convert(Collection<? extends GrantedAuthority> authorities) {
    return authorities.stream().map(Object::toString).collect(Collectors.toList());
  }

  public Collection<? extends GrantedAuthority> convert(List<String> authorities) {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public Map<String, Object> toMap(UserPrincipal userPrincipal) {
    Map<String, Object> user = new HashMap<>();
    user.put("id", userPrincipal.getId());
    user.put("username", userPrincipal.getUsername());
    user.put("email", userPrincipal.getEmail());
    user.put("password", userPrincipal.getPassword());
    user.put("authority", convert(userPrincipal.getAuthorities()));
    return user;
  }

  @SuppressWarnings("unchecked")
  public UserPrincipal toUserPrincipal(Claims claims) {
    UserPrincipal userPrincipal = new UserPrincipal();
    userPrincipal.setId(Long.valueOf(String.valueOf(claims.get("id"))));
    userPrincipal.setUsername(String.valueOf(claims.get("username")));
    userPrincipal.setEmail(String.valueOf(claims.get("email")));
    userPrincipal.setPassword(String.valueOf(claims.get("password")));
    userPrincipal.setAuthorities(convert((List<String>) claims.get("authority")));
    return userPrincipal;
  }
}
