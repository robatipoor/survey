package com.snap.survey.model.request;


import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateQuestionRequest(
    @NotEmpty String content, @NotNull List<CreateChoiceRequest> choices) {}
