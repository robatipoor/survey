package com.snap.survey.model;

import javax.validation.constraints.NotEmpty;

public record SubmitAnswerRequest(@NotEmpty String questionCode, @NotEmpty String choiceCode) {}
