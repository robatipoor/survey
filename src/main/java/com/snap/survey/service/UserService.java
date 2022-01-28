package com.snap.survey.service;

import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.LoginRequest;
import com.snap.survey.model.request.RegisterRequest;
import com.snap.survey.model.response.LoginResponse;
import java.util.function.Function;

public interface UserService {

  Long save(UserEntity user);

  void update(Long userId, Function<UserEntity, UserEntity> func);

  UserEntity getByUserId(Long userId);

  LoginResponse loginUser(LoginRequest loginRequest);

  void registerUser(RegisterRequest registerRequest);

  UserEntity createUserEntityFromRequest(RegisterRequest registerRequest);
}
