package com.snap.survey.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public record SubmitAnswerRequest(
    @JsonProperty("question_id") @NotNull Long questionId,
    @JsonProperty("choice_id") @NotNull Long choiceId) {}
