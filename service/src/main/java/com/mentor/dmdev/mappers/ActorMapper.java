package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.ActorReadDto;
import com.mentor.dmdev.entity.Actor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorReadDto map(Actor source);

    List<ActorReadDto> map(List<Actor> source);
}