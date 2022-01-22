package com.snap.survey.model.response;

import java.util.List;

public record QuestionResponse(Long id, String content, List<ChoiceResponse> choices) {}
