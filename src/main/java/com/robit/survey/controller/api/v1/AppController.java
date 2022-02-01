package com.robit.survey.controller.api.v1;

import com.robit.survey.model.response.BaseResponse;
import com.robit.survey.util.BaseResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app")
public class AppController {

  private final BaseResponseUtil baseResponseUtil;

  public AppController(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @GetMapping("/ping")
  public ResponseEntity<BaseResponse<String>> ping() {
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse("Pong"));
  }
}
