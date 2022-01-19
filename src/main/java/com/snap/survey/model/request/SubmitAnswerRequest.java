package com.snap.survey.model.request;

import javax.validation.constraints.NotEmpty;

public record SubmitAnswerRequest(@NotEmpty String questionCode, @NotEmpty String choiceCode) {}
