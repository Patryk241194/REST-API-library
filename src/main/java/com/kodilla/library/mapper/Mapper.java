package com.kodilla.library.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Dto, Entity> {

    Dto mapToDto(Entity entity);

    Entity mapToEntity(Dto dto);

    default List<Dto> mapToDtoList(List<Entity> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    default List<Entity> mapToEntityList(List<Dto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
