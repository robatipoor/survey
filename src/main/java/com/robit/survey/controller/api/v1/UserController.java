package com.robit.survey.controller.api.v1;

import com.robit.survey.model.request.LoginRequest;
import com.robit.survey.model.request.RegisterRequest;
import com.robit.survey.model.response.BaseResponse;
import com.robit.survey.model.response.LoginResponse;
import com.robit.survey.service.UserService;
import com.robit.survey.util.BaseResponseUtil;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
  private final BaseResponseUtil baseResponseUtil;
  private final UserService userService;

  public UserController(BaseResponseUtil baseResponseUtil, UserService userService) {
    this.baseResponseUtil = baseResponseUtil;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponse>> login(
      @RequestBody @Valid LoginRequest loginRequest) {
    log.info("receive request authenticate user : {}", loginRequest.usernameOrEmail());
    var response = userService.loginUser(loginRequest);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @PostMapping("/register")
  public ResponseEntity<BaseResponse<?>> register(
      @RequestBody @Valid RegisterRequest registerRequest) {
    log.info("receive request register user : {}", registerRequest.username());
    userService.registerUser(registerRequest);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(null));
  }

  // TODO update user
}
