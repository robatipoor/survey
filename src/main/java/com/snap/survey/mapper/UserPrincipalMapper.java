package com.snap.survey.mapper;

import com.snap.survey.entity.RoleEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.UserPrincipal;
import java.util.Collection;
import java.util.Set;
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
  public Collection<? extends GrantedAuthority> rolesToAuthorities(Set<RoleEntity> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
        .collect(Collectors.toList());
  }
}
