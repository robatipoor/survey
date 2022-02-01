package com.robit.survey.controller.api.v1;

import com.robit.survey.config.Constants;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.model.response.BaseResponse;
import com.robit.survey.model.response.QuestionResponse;
import com.robit.survey.service.QuestionService;
import com.robit.survey.util.BaseResponseUtil;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/question")
@Slf4j
public class QuestionController {

  private final BaseResponseUtil baseResponseUtil;
  private final QuestionService questionService;

  public QuestionController(BaseResponseUtil baseResponseUtil, QuestionService questionService) {
    this.baseResponseUtil = baseResponseUtil;
    this.questionService = questionService;
  }

  @GetMapping("/one/{id}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<QuestionResponse>> getOneByAdmin(
      Authentication authentication, @NotNull @PathVariable Long id) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get one question userId : {}", userId);
    var response = questionService.getOne(userId, id);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/list/{slug}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<Page<QuestionResponse>>> getPageByAdmin(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get one question userId : {}", userId);
    var response = questionService.getByUserIdAndSurveySlug(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  // TODO update question
}
