package com.robit.survey.controller.api.v1;

import com.robit.survey.config.Constants;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.model.request.CreateSurveyRequest;
import com.robit.survey.model.request.SubmitSurveyRequest;
import com.robit.survey.model.response.*;
import com.robit.survey.service.SurveyService;
import com.robit.survey.util.BaseResponseUtil;
import com.snap.survey.model.response.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SurveyController {

  private final BaseResponseUtil baseResponseUtil;
  private final SurveyService surveyService;

  public SurveyController(BaseResponseUtil baseResponseUtil, SurveyService surveyService) {
    this.baseResponseUtil = baseResponseUtil;
    this.surveyService = surveyService;
  }

  @GetMapping("/{slug}")
  public ResponseEntity<BaseResponse<Page<QuestionResponse>>> readQuestion(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request read question userId : {} slug : {}", userId, slug);
    var response = surveyService.readQuestion(slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @PostMapping("/create")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<CreateSurveyResponse>> create(
      Authentication authentication, @Valid @RequestBody CreateSurveyRequest request) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request create survey userId : {}", userId);
    var response = surveyService.create(userId, request);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/list")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<Page<SurveyResponse>>> getPage(
      Authentication authentication, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get page survey userId : {}", userId);
    var response = surveyService.getPage(userId, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/result/{slug}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<ResultSurveyResponse>> getResult(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get result userId : {}", userId);
    var response = surveyService.getResult(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @PostMapping("/submit/{slug}")
  public ResponseEntity<BaseResponse<Void>> submit(
      Authentication authentication,
      @Valid @RequestBody SubmitSurveyRequest request,
      @NotEmpty @PathVariable String slug) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request submit userId : {}", userId);
    surveyService.submit(userId, slug, request);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(null));
  }

  // TODO update survey
}
