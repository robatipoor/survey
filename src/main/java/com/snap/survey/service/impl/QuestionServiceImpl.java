package com.snap.survey.service.impl;

import com.snap.survey.model.response.QuestionResponse;
import com.snap.survey.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

  public Page<QuestionResponse> getPage(Long userId, String slug, Pageable page) {
    // TODO impl
    return null;
  }

  public QuestionResponse getOne(Long userId, Long questionId, Pageable page) {
    // TODO impl
    return null;
  }
}
