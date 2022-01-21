package com.snap.survey.mapper;

import com.snap.survey.entity.AnswerEntity;
import com.snap.survey.model.request.SubmitAnswerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {
  public abstract AnswerEntity toEntity(SubmitAnswerRequest request);
}
