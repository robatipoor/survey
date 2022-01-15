package com.snap.survey.mapper;

import com.snap.survey.entity.UserEntity;
import com.snap.survey.model.request.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends BaseMapper<RegisterRequest, UserEntity> {}
