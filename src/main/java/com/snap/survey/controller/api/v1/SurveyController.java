package com.snap.survey.controller.api.v1;

import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.util.BaseResponseUtil;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/survey")
public class SurveyController {

  private final BaseResponseUtil baseResponseUtil;

  public SurveyController(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @PostMapping("/create")
  // Role Admin
  public ResponseEntity<BaseResponse<CreateSurveyResponse>> create(
      @Valid @RequestBody CreateSurveyRequest request) {
    // TODO impl
    return null;
  }

  @GetMapping("/list")
  // Role Admin
  public ResponseEntity<BaseResponse<Page<SurveyResponse>>> getPage(Pageable page) {
    // TODO impl
    return null;
  }

  @GetMapping("/result/{slug}")
  // Role Admin
  public ResponseEntity<BaseResponse<ResultSurveyResponse>> getResult(
      @NotEmpty @PathVariable String slug, Pageable page) {
    // TODO impl
    return null;
  }

  @PostMapping("/submit/{slug}")
  public ResponseEntity<BaseResponse<Void>> submit(
      @Valid @RequestBody SubmitSurveyRequest request, @NotEmpty @PathVariable String slug) {
    // TODO impl
    return null;
  }
}
