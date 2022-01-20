package com.snap.survey.service;

import com.snap.survey.model.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
  Page<QuestionResponse> getPage(Long userId, String slug, Pageable page);

  QuestionResponse getOne(Long userId, Long questionId, Pageable page);
}
