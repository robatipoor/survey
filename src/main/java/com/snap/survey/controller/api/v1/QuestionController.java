package com.snap.survey.controller.api.v1;

import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.QuestionResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/question")
public class QuestionController {

  @GetMapping("/{slug}")
  public ResponseEntity<BaseResponse<Page<QuestionResponse>>> getPage(
      @NotEmpty @PathVariable String slug, Pageable page) {
    // TODO impl
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<QuestionResponse>> getOne(
      @NotNull @PathVariable Long id, Pageable page) {
    // TODO impl
    return null;
  }
}
