package com.robit.survey.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerResponse(
    @JsonProperty("user_id") Long userId,
    @JsonProperty("question_id") Long questionId,
    @JsonProperty("answer_id") Long answerId,
    ChoiceResponse choice) {}
