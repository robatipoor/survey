package com.snap.survey.service;

import com.snap.survey.model.response.AnswerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {
  Page<AnswerResponse> getPage(Long userId, String slug, Pageable page);

  AnswerResponse getOne(Long userId, Long answerId, Pageable page);
}
