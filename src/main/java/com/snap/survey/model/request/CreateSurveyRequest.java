package com.snap.survey.model.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateSurveyRequest(
    @NotEmpty @Size(min = 2, max = 50) String title,
    @NotNull List<CreateQuestionRequest> questions,
    @NotNull Integer expireDay) {}
