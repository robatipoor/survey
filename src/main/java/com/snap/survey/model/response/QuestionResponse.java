package com.snap.survey.model.response;

import com.snap.survey.model.Choice;
import java.util.List;

public record QuestionResponse(String code, String content, List<Choice> choices) {}
