package com.snap.survey.controller.api.v1;

import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.util.BaseResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {
  private final BaseResponseUtil baseResponseUtil;

  public SurveyController(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse<?>> create() {
    // TODO impl
    return null;
  }

  @GetMapping
  public ResponseEntity<BaseResponse<?>> read() {
    // TODO impl
    return null;
  }

  @GetMapping("/list")
  public ResponseEntity<BaseResponse<?>> list() {
    // TODO impl
    return null;
  }

  @GetMapping("/result")
  public ResponseEntity<BaseResponse<?>> result() {
    // TODO impl
    return null;
  }

  @PostMapping("/submit")
  public ResponseEntity<BaseResponse<?>> submit() {
    // TODO impl
    return null;
  }
}
