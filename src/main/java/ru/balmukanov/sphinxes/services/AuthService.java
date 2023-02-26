package ru.balmukanov.sphinxes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.entities.User;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

import java.util.Objects;

@Slf4j
@Service("authService")
@RequiredArgsConstructor
public class AuthService {
    private final QuestionnaireRepository questionnaireRepository;

    public boolean hasQuestionnaireAccess(long id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Questionnaire questionnaire = questionnaireRepository.findByIdWithUser(id);

        if (Objects.isNull(questionnaire.getCreator()) || questionnaire.getCreator().getId() != principal.getId()) {
            log.debug("The questionnaire={}} is not owned by the user={}}", questionnaire.getId(), principal.getId());
            return false;
        }

        return true;
    }
}
