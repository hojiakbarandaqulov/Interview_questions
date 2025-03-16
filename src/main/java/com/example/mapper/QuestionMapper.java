package com.example.mapper;

import com.example.dto.question.QuestionCreateDTO;
import com.example.dto.question.QuestionPaginationDTO;
import com.example.entity.QuestionCreateEntity;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionCreateDTO toDTO(QuestionCreateEntity entity);
    QuestionCreateEntity toEntity(QuestionCreateDTO dto);
    QuestionPaginationDTO toPaginationDTO(QuestionCreateEntity entity);

}
