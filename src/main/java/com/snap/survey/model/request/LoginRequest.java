package com.snap.survey.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public record LoginRequest(
    @JsonProperty("username_or_email") @NotBlank String usernameOrEmail,
    @NotBlank String password) {}
