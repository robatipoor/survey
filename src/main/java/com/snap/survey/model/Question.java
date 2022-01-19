package com.snap.survey.model;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record Question(@NotEmpty String content, @NotNull List<Choice> choices) {}
