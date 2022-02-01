package com.robit.survey.mapper;

import com.robit.survey.entity.AnswerEntity;
import com.robit.survey.model.response.AnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {

  @Mapping(source = "answer.user.id", target = "userId")
  @Mapping(source = "answer.question.id", target = "questionId")
  @Mapping(source = "answer.id", target = "answerId")
  public abstract AnswerResponse toResponse(AnswerEntity answer);
}
