package ru.balmukanov.sphinxes.services.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.dto.response.QuestionDto;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.exception.EditQuestionException;
import ru.balmukanov.sphinxes.exception.QuestionNotFoundException;
import ru.balmukanov.sphinxes.mappers.QuestionMapper;
import ru.balmukanov.sphinxes.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public void create(CreateQuestionDto request) {
        Question question = questionMapper.mapToQuestion(request);
        questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> findAll() {
        return questionRepository.findAll().stream().map(questionMapper::mapToQuestionDto).toList();
    }

    @Override
    @Transactional
    public QuestionDto findById(long id) {
        return questionRepository.findById(id)
                .map(questionMapper::mapToQuestionDto)
                .orElseThrow(() -> new QuestionNotFoundException("Question by id=%d not found".formatted(id)));
    }

    @Override
    @Transactional
    public void edit(long id, QuestionDto questionDto) {
        if (questionDto.getLinks().isBlank()) {
            questionDto.setLinks(null);
        }
        Question newQuestion = questionMapper.mapToQuestion(questionDto);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question by id=%d not found".formatted(id)));
        if (question.getId() != newQuestion.getId()) {
            throw new EditQuestionException("Id not equals, new id=%d odl id=%d".formatted(newQuestion.getId(),
                    question.getId()));
        }

        questionRepository.update(newQuestion);
    }
}
