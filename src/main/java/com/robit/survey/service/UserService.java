package com.robit.survey.service;

import com.robit.survey.entity.UserEntity;
import com.robit.survey.model.request.LoginRequest;
import com.robit.survey.model.request.RegisterRequest;
import com.robit.survey.model.response.LoginResponse;
import java.util.function.Function;

public interface UserService {

  Long save(UserEntity user);

  void update(Long userId, Function<UserEntity, UserEntity> func);

  UserEntity getByUserId(Long userId);

  LoginResponse loginUser(LoginRequest loginRequest);

  void registerUser(RegisterRequest registerRequest);

  UserEntity createUserEntityFromRequest(RegisterRequest registerRequest);
}
