package ru.balmukanov.sphinxes.services.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.dto.response.QuestionDto;
import ru.balmukanov.sphinxes.entities.Question;
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
}
