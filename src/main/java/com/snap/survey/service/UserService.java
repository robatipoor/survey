package com.snap.survey.service;

import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.LoginRequest;
import com.snap.survey.model.request.RegisterRequest;
import com.snap.survey.model.response.LoginResponse;

public interface UserService {

  UserEntity getByUserId(Long userId);

  LoginResponse loginUser(LoginRequest loginRequest);

  void registerUser(RegisterRequest registerRequest);

  UserEntity createUserEntityFromRequest(RegisterRequest registerRequest);
}
