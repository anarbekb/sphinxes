package ru.balmukanov.sphinxes.services.questionnaire;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.entities.Feedback;
import ru.balmukanov.sphinxes.repository.AnswerQuestionRepository;
import ru.balmukanov.sphinxes.repository.FeedbackRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AnswerQuestionRepository answerQuestionRepository;

    @Override
    public void create(CompleteQuestionnaireDto completeQuestionnaireDto) {
        List<String> improves = new ArrayList<>();
        answerQuestionRepository.findFailed(completeQuestionnaireDto.getId()).forEach(answerQuestion -> {
            String improve = answerQuestion.getSubject();
            if (answerQuestion.getLinks() != null) {
                improve = improve + ": " + answerQuestion.getLinks();
            }
            improves.add(improve);
        });

        Feedback feedback = new Feedback();
        feedback.setNeedImprove(String.join(" , ", improves));
        feedback.setWeaknesses(completeQuestionnaireDto.getWeaknesses());
        feedback.setStrengths(completeQuestionnaireDto.getStrengths());
        feedback.setQuestionnaireId(completeQuestionnaireDto.getId());
        feedbackRepository.save(feedback);
    }
}
