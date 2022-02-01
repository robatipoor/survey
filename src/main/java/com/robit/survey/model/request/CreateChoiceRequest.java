package com.robit.survey.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateChoiceRequest(
    @NotNull Integer number, @NotEmpty @Size(min = 2, max = 50) String content) {}
