package com.snap.survey.model.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateSurveyRequest(
    @NotEmpty String title,
    @NotNull List<CreateQuestionRequest> questions,
    @NotNull String expireDate) {}
