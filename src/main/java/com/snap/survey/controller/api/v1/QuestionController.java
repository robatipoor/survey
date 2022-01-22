package com.snap.survey.controller.api.v1;

import com.snap.survey.config.Constants;
import com.snap.survey.model.UserPrincipal;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.service.QuestionService;
import com.snap.survey.util.BaseResponseUtil;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class QuestionController {

  private final BaseResponseUtil baseResponseUtil;
  private final QuestionService questionService;

  public QuestionController(BaseResponseUtil baseResponseUtil, QuestionService questionService) {
    this.baseResponseUtil = baseResponseUtil;
    this.questionService = questionService;
  }

  @GetMapping("/{slug}")
  public ResponseEntity<BaseResponse<Page<QuestionResponse>>> getPage(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = questionService.getPage(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/{id}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<QuestionResponse>> getOne(
      Authentication authentication, @NotNull @PathVariable Long id) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = questionService.getOne(userId, id);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }
}
