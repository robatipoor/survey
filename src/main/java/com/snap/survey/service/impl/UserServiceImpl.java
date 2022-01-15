package com.snap.survey.service.impl;

import com.snap.survey.config.Constants;
import com.snap.survey.entity.RoleEntity;
import com.snap.survey.entity.UserEntity;
import com.snap.survey.mapper.UserMapper;
import com.snap.survey.model.UserPrincipal;
import com.snap.survey.model.enums.Role;
import com.snap.survey.model.request.LoginRequest;
import com.snap.survey.model.request.RegisterRequest;
import com.snap.survey.model.response.LoginResponse;
import com.snap.survey.repository.RoleRepository;
import com.snap.survey.repository.UserRepository;
import com.snap.survey.service.UserService;
import com.snap.survey.util.AppExceptionUtil;
import com.snap.survey.util.TokenUtil;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final AppExceptionUtil appExceptionUtil;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final TokenUtil tokenUtil;

  public UserServiceImpl(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      AppExceptionUtil appExceptionUtil,
      PasswordEncoder passwordEncoder,
      UserMapper userMapper,
      TokenUtil tokenUtil) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.appExceptionUtil = appExceptionUtil;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.tokenUtil = tokenUtil;
  }

  @Override
  public LoginResponse loginUser(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.usernameOrEmail(), loginRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("success user : {} login application ", loginRequest.usernameOrEmail());
    String jwtToken = tokenUtil.generateToken((UserPrincipal) authentication.getPrincipal());
    return new LoginResponse(jwtToken, Constants.BEARER);
  }

  @Override
  @Transactional
  public void registerUser(RegisterRequest registerRequest) {
    if (userRepository.existsByUsernameOrEmail(
        registerRequest.username(), registerRequest.email())) {
      throw appExceptionUtil.getAppException(
          "email.or.username.already.exist.error.message",
          "email.or.username.already.exist.error.code");
    }
    UserEntity user = createUserEntityFromRequest(registerRequest);
    try {
      UserEntity result = userRepository.save(user);
      log.info("success new register userId : {}", result.getId());
    } catch (Exception e) {
      log.error("register user failed exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException(
          "save.exception.error.message", "save.exception.error.code");
    }
  }

  @Override
  public UserEntity createUserEntityFromRequest(RegisterRequest registerRequest) {
    UserEntity user = userMapper.toEntity(registerRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    RoleEntity role =
        roleRepository
            .findByRoleName(Role.ROLE_USER)
            .orElseThrow(
                () -> {
                  log.error("role not found");
                  return appExceptionUtil.getAppException(
                      "not.found.exception.error.message", "not.found.exception.error.code");
                });
    user.setRoles(Set.of(role));
    // TODO active process
    user.setActivated(true);
    return user;
  }
}
