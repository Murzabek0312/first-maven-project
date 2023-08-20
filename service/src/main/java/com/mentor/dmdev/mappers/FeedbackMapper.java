package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.FeedBackReadDto;
import com.mentor.dmdev.entity.FeedBack;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedBackReadDto map(FeedBack source);

    List<FeedBackReadDto> map(List<FeedBack> source);
}