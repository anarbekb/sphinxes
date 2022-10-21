package ru.balmukanov.sphinxes.mappers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.entities.Questionnaire;

@Mapper(componentModel = "spring")
public interface QuestionnaireMapper {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd.MM.yyyy")
            .withZone(ZoneId.from(ZoneOffset.UTC));

    @Mapping(target = "createdDt", source = "createdDt", qualifiedByName = "createdDtToString")
    QuestionnaireDto mapToDto(Questionnaire questionnaire);

    @Mapping(target = "candidate", source = "candidateFullName")
    @Mapping(target = "createdDt", expression = "java( java.time.Instant.now() )")
    @Mapping(target = "status", expression = "java( ru.balmukanov.sphinxes.entities.QuestionnaireStatus.PROGRESS )")
    Questionnaire mapFromRequest(CreateQuestionnaireDto request);

    @Named("createdDtToString")
    static String createdDtToString(Instant instant) {
        return DATE_TIME_FORMATTER.format(instant);
    }
}
