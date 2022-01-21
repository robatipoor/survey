package com.snap.survey.controller.api.v1;

import com.snap.survey.model.UserPrincipal;
import com.snap.survey.model.response.AnswerResponse;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.service.AnswerService;
import com.snap.survey.util.BaseResponseUtil;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/answer")
public class AnswerController {

  private final BaseResponseUtil baseResponseUtil;
  private final AnswerService answerService;

  public AnswerController(BaseResponseUtil baseResponseUtil, AnswerService answerService) {
    this.baseResponseUtil = baseResponseUtil;
    this.answerService = answerService;
  }

  @GetMapping("/{slug}")
  // role admin
  public ResponseEntity<BaseResponse<Page<AnswerResponse>>> getPage(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = answerService.getPage(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/{id}")
  // role admin
  public ResponseEntity<BaseResponse<AnswerResponse>> getOne(
      Authentication authentication, @NotNull @PathVariable Long id) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    var response = answerService.getOne(userId, id);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }
}
