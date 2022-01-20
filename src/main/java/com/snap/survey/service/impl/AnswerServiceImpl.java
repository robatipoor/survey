package com.snap.survey.service.impl;

import com.snap.survey.model.response.AnswerResponse;
import com.snap.survey.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

  public Page<AnswerResponse> getPage(Long userId, String slug, Pageable page) {
    // TODO impl
    return null;
  }

  public AnswerResponse getOne(Long userId, Long answerId, Pageable page) {
    // TODO impl
    return null;
  }
}
