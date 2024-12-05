package com.example.mapper;

import com.example.dto.question.QuestionCreateDTO;
import com.example.entity.QuestionEntity;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionCreateDTO toDTO(QuestionEntity entity);
    QuestionEntity toEntity(QuestionCreateDTO dto);


}
