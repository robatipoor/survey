package com.robit.survey.mapper;

import com.robit.survey.entity.QuestionEntity;
import com.robit.survey.model.request.CreateQuestionRequest;
import com.robit.survey.model.response.QuestionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {
  public abstract QuestionEntity toEntity(CreateQuestionRequest request);

  public abstract QuestionResponse toResponse(QuestionEntity question);
}
