package com.snap.survey.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record LoginRequest(
    @JsonProperty("username_or_email") @NotBlank @Size(max = 50) String usernameOrEmail,
    @NotBlank String password) {}
