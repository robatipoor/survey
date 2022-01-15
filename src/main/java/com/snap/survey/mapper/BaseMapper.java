package com.snap.survey.mapper;

import java.util.Set;

public abstract class BaseMapper<D, E> {

  public abstract E toEntity(D dto);

  public abstract D toDto(E entity);

  public abstract Set<E> toEntity(Set<D> dtos);

  public abstract Set<D> toDto(Set<E> entities);
}
