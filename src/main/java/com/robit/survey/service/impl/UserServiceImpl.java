package com.robit.survey.service.impl;

import com.robit.survey.config.Constants;
import com.robit.survey.entity.UserEntity;
import com.robit.survey.mapper.UserMapper;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.model.enums.Role;
import com.robit.survey.model.request.LoginRequest;
import com.robit.survey.model.request.RegisterRequest;
import com.robit.survey.model.response.LoginResponse;
import com.robit.survey.repository.RoleRepository;
import com.robit.survey.repository.UserRepository;
import com.robit.survey.service.UserService;
import com.robit.survey.util.AppExceptionUtil;
import com.robit.survey.util.TokenUtil;
import java.util.Set;
import java.util.function.Function;
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
    this.save(user);
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

  @Override
  @Transactional
  public Long save(UserEntity user) {
    try {
      var result = userRepository.save(user);
      log.info("success save userId : {}", result.getId());
      return result.getId();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("save user entity failed exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("save.exception.error", e.getMessage());
    }
  }

  @Transactional
  public void update(Long userId, Function<UserEntity, UserEntity> func) {
    userRepository
        .findById(userId)
        .map(func)
        .ifPresent(
            user -> {
              this.save(user);
              log.info("success update userId : {}", userId);
            });
  }
}
