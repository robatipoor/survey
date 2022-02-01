package com.robit.survey.mapper;

import com.robit.survey.entity.UserEntity;
import com.robit.survey.model.request.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends BaseMapper<RegisterRequest, UserEntity> {}
