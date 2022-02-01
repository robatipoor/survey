package com.robit.survey.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateQuestionRequest(
    @NotEmpty @Size(min = 2, max = 50) String content,
    @JsonProperty("choices") @NotNull List<CreateChoiceRequest> choicesRequest) {}
