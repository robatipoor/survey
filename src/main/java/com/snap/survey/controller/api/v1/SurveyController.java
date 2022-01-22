package com.snap.survey.controller.api.v1;

import com.snap.survey.config.Constants;
import com.snap.survey.model.UserPrincipal;
import com.snap.survey.model.request.CreateSurveyRequest;
import com.snap.survey.model.request.SubmitSurveyRequest;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.CreateSurveyResponse;
import com.snap.survey.model.response.ResultSurveyResponse;
import com.snap.survey.model.response.SurveyResponse;
import com.snap.survey.service.SurveyService;
import com.snap.survey.util.BaseResponseUtil;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/survey")
public class SurveyController {

  private final BaseResponseUtil baseResponseUtil;
  private final SurveyService surveyService;

  public SurveyController(BaseResponseUtil baseResponseUtil, SurveyService surveyService) {
    this.baseResponseUtil = baseResponseUtil;
    this.surveyService = surveyService;
  }

  @PostMapping("/create")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<CreateSurveyResponse>> create(
      Authentication authentication, @Valid @RequestBody CreateSurveyRequest request) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = surveyService.create(userId, request);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/list")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<Page<SurveyResponse>>> getPage(
      Authentication authentication, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = surveyService.getPage(userId, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/result/{slug}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<ResultSurveyResponse>> getResult(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = surveyService.getResult(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @PostMapping("/submit/{slug}")
  public ResponseEntity<BaseResponse<Void>> submit(
      Authentication authentication,
      @Valid @RequestBody SubmitSurveyRequest request,
      @NotEmpty @PathVariable String slug) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    surveyService.submit(userId, slug, request);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(null));
  }
}
