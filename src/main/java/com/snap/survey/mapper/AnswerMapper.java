package com.snap.survey.mapper;

import com.snap.survey.entity.AnswerEntity;
import com.snap.survey.model.request.SubmitAnswerRequest;
import com.snap.survey.model.response.AnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {
  public abstract AnswerEntity toEntity(SubmitAnswerRequest request);

  @Mapping(source = "answer.user.id", target = "userId")
  @Mapping(source = "answer.question.id", target = "questionId")
  @Mapping(source = "answer.id", target = "answerId")
  public abstract AnswerResponse toResponse(AnswerEntity answer);
}
