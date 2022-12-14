package ru.balmukanov.sphinxes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "id", ignore = true)
    AnswerQuestion mapToAnswerQuestion(Question question);
}
