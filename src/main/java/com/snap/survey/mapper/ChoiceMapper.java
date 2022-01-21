package com.snap.survey.mapper;

import com.snap.survey.entity.ChoiceEntity;
import com.snap.survey.model.request.CreateChoiceRequest;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ChoiceMapper {
  public abstract ChoiceEntity toEntity(CreateChoiceRequest request);

  public abstract List<ChoiceEntity> toEntity(List<CreateChoiceRequest> requests);
}
