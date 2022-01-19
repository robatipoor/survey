package com.snap.survey.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AnswerResultResponse(
    @JsonProperty("question_code") String questionCode,
    @JsonProperty("question_content") String questionContent,
    List<AnswerChoiceResultResponse> answers) {}
