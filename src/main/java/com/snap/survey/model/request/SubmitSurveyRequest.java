package com.snap.survey.model.request;

import com.snap.survey.model.SubmitAnswerRequest;
import java.util.List;
import javax.validation.constraints.NotNull;

public record SubmitSurveyRequest(@NotNull List<SubmitAnswerRequest> answers) {}
