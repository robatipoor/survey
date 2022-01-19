package com.snap.survey.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerResponse(
    @JsonProperty("user_code") String userCode,
    @JsonProperty("question_code") String questionCode,
    @JsonProperty("answer_code") String answerCode,
    ChoiceResponse choice) {}
