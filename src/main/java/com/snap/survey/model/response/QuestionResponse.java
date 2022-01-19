package com.snap.survey.model.response;

import java.util.List;

public record QuestionResponse(String code, String content, List<ChoiceResponse> choices) {}
