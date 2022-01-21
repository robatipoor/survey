package com.snap.survey.mapper;

import com.snap.survey.entity.QuestionEntity;
import com.snap.survey.model.request.CreateQuestionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {
  public abstract QuestionEntity toEntity(CreateQuestionRequest request);
}
