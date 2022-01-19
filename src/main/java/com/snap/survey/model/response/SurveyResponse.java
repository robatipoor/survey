package com.snap.survey.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO count questions
public record SurveyResponse(
    String title, String slug, @JsonProperty("is_expired") boolean isExpired) {}
