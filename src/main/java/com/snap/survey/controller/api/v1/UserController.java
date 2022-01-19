package com.snap.survey.controller.api.v1;

import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.util.BaseResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {
  private final BaseResponseUtil baseResponseUtil;

  public UserController(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @GetMapping("/login")
  public ResponseEntity<BaseResponse<?>> login() {
    // TODO impl
    return null;
  }

  @PostMapping("/register")
  public ResponseEntity<BaseResponse<?>> register() {
    // TODO impl
    return null;
  }
}
