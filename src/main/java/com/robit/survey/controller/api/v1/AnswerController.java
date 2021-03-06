package com.robit.survey.controller.api.v1;

import com.robit.survey.config.Constants;
import com.robit.survey.model.UserPrincipal;
import com.robit.survey.model.response.AnswerResponse;
import com.robit.survey.model.response.BaseResponse;
import com.robit.survey.service.AnswerService;
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
@RequestMapping("/api/v1/answer")
@Slf4j
public class AnswerController {

  private final BaseResponseUtil baseResponseUtil;
  private final AnswerService answerService;

  public AnswerController(BaseResponseUtil baseResponseUtil, AnswerService answerService) {
    this.baseResponseUtil = baseResponseUtil;
    this.answerService = answerService;
  }

  @GetMapping("/list/{slug}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<Page<AnswerResponse>>> getPageByAdmin(
      Authentication authentication, @NotEmpty @PathVariable String slug, Pageable page) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get one page slug : {} userId : {} ", slug, userId);
    var response = answerService.getPage(userId, slug, page);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }

  @GetMapping("/one/{id}")
  @PreAuthorize(Constants.ADMIN)
  public ResponseEntity<BaseResponse<AnswerResponse>> getOneByAdmin(
      Authentication authentication, @NotNull @PathVariable Long id) {
    Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
    log.info("receive request get one answerId : {} userId : {} ", id, userId);
    var response = answerService.getOne(userId, id);
    return ResponseEntity.ok(baseResponseUtil.getSuccessResponse(response));
  }
}
