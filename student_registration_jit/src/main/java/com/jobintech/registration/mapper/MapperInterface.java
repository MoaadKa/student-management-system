package com.jobintech.registration.mapper;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface MapperInterface<E, D> {

    E mapToEntity(D dto);

    default List<E> mapToEntity(List<D> dtos){
        return dtos == null || dtos.isEmpty()?
                Collections.emptyList() :
                dtos.stream().map(this::mapToEntity).collect(Collectors.toList());
    };

    D mapToDto(E e);

    default List<D> mapToDto(List<E> entities){
        return entities == null || entities.isEmpty()?
                Collections.emptyList() :
                entities.stream().map(this::mapToDto).collect(Collectors.toList());
    };
}
