package ru.balmukanov.sphinxes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.dto.response.QuestionDto;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "id", ignore = true)
    AnswerQuestion mapToAnswerQuestion(Question question);

    Question mapToQuestion(CreateQuestionDto request);

    QuestionDto mapToQuestionDto(Question question);
}
