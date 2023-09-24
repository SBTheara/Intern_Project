package com.intern.project.service;

import com.intern.project.entity.Answer;
import com.intern.project.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    public Answer save(Answer answer){
        return answerRepository.save(answer);
    }

    public List<Answer> listAll(){
        return answerRepository.findAll();
    }
}
