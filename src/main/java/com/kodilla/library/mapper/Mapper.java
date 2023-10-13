package com.kodilla.library.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<D, E> {

    D mapToDto(E e);

    E mapToEntity(D d);

    default List<D> mapToDtoList(List<E> eList) {
        return eList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    default List<E> mapToEntityList(List<D> dList) {
        return dList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
