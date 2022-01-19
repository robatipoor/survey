package com.snap.survey.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record ResultSurveyResponse(
    String title,
    @JsonProperty("is_finished") boolean is_finished,
    Page<AnswerResultResponse> results,
    @JsonProperty("number_of_participants") int numberOfParticipants) {}
