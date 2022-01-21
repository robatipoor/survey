package com.snap.survey.controller.api.v1;

import com.snap.survey.model.request.LoginRequest;
import com.snap.survey.model.request.RegisterRequest;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.LoginResponse;
import com.snap.survey.service.UserService;
import com.snap.survey.util.BaseResponseUtil;
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

  @GetMapping("/login")
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
}
