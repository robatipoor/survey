package com.snap.survey.controller.api.v1;

import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.util.BaseResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseController {

  private final BaseResponseUtil baseResponseUtil;

  public BaseController(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @GetMapping("/ping")
  public ResponseEntity<BaseResponse<String>> ping() {
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse("Pong"));
  }
}
