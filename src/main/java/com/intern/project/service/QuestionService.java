package com.intern.project.service;

import com.intern.project.dto.QuestionResponse;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Questions;
import com.intern.project.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final ModelMapper modelMapper;

    public Questions save(Questions request) {
        Questions questions = new Questions();
        List<Answer> answerList = request.getAnswers();
        Answer answer = new Answer();
        request.setAnswers(null);
        long id = getid(questionRepository.save(request));
        answerList.forEach(r->r.setQuestionId((int) id));
        request.setAnswers(answerList);
        return questionRepository.save(request);
    }

    private long getid(Questions save) {
        return save.getId();
    }

    public List<QuestionResponse> listAll() {
        return questionRepository.findAll()
                .stream()
                .map(question -> this.modelMapper.map(question, QuestionResponse.class))
                .toList();
    }
}
