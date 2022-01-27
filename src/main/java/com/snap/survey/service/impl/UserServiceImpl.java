package com.snap.survey.service.impl;

import com.snap.survey.config.Constants;
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
  public UserEntity getByUserId(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> appExceptionUtil.getBusinessException("user.not.found.error"));
  }

  @Override
  public LoginResponse loginUser(LoginRequest loginRequest) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.usernameOrEmail(), loginRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("success user : {} login application ", loginRequest.usernameOrEmail());
    var jwtToken = tokenUtil.generateToken((UserPrincipal) authentication.getPrincipal());
    return new LoginResponse(jwtToken, Constants.BEARER);
  }

  @Override
  @Transactional
  public void registerUser(RegisterRequest registerRequest) {
    if (userRepository.existsByUsernameOrEmail(
        registerRequest.username(), registerRequest.email())) {
      throw appExceptionUtil.getBusinessException("email.or.username.already.exist.error");
    }
    var user = createUserEntityFromRequest(registerRequest);
    try {
      var result = userRepository.save(user);
      log.info("success new register userId : {}", result.getId());
    } catch (Exception e) {
      e.printStackTrace();
      log.error("register user failed exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.exception.error", e.getMessage());
    }
  }

  @Override
  public UserEntity createUserEntityFromRequest(RegisterRequest registerRequest) {
    var user = userMapper.toEntity(registerRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    var role =
        roleRepository
            .findByRoleName(Role.ROLE_USER)
            .orElseThrow(
                () -> {
                  log.error("role not found");
                  return appExceptionUtil.getBusinessException("not.found.exception.error");
                });
    user.setRoles(Set.of(role));
    // TODO active process
    user.setActivated(true);
    return user;
  }
}
