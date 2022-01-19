package com.snap.survey.model.response;

import com.snap.survey.model.Choice;

public record AnswerResponse(
    String userCode, String questionCode, String answerCode, Choice choice) {}
