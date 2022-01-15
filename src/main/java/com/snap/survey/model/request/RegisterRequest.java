package com.snap.survey.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(min = 2, max = 50) String firstName,
    @NotBlank @Size(min = 2, max = 50) String lastName,
    @NotBlank @Size(min = 2, max = 50) String username,
    @NotBlank @Size(max = 50) @Email String email,
    @NotBlank @Size(min = 8, max = 50) String password) {}
