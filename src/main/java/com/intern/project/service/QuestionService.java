package com.intern.project.service;

import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Questions;
import com.intern.project.repository.QuestionRepository;
import com.intern.project.utils.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public QuestionDTO save(QuestionDTO request) {
        Questions questions;
        if (isInvalidQuestion(request)) {
            questions = this.saveNewQuestion(request);
        } else {
            questions = this.getQuestion(request);
            if (isValidAnswer(request)) {
                this.updateQuestionOrAnswer(questions, request);
            }else {
                this.getNewAnswer(questions, request);
            }
        }
        return this.modelMapper.map(questions, QuestionDTO.class);
    }

    private Questions getQuestion(QuestionDTO request) {
        return this.questionRepository
                .findById(request.getId())
                .orElseThrow(() -> new IllegalStateException("Not found for this question !! "));
    }

    private boolean isInvalidQuestion(QuestionDTO request) {
        return request.getId() == null || request.getId() <= 0L;
    }

    private boolean isValidAnswer(QuestionDTO request) {
        return request.getAnswers().stream().anyMatch(answerRequest -> answerRequest.getId() != null && answerRequest.getId() != 0L);
    }

    private void getNewAnswer(Questions questions, QuestionDTO request) {
        List<Answer> listAnswer = request.getAnswers().stream()
                .map(answerRequest -> {
                    Answer answer = new Answer();
                    this.modelMapper.map(answerRequest, answer);
                    answer.setQuestions(questions);
                    return answer;
                }).toList();
        questions.setAnswers(listAnswer);
        this.modelMapper.map(request, questions);
        questionRepository.save(questions);
    }

    private void updateQuestionOrAnswer(Questions questions, QuestionDTO request) {
        List<Answer> listAnswer = request.getAnswers().stream()
                .map(answerRequest -> {
                    Answer answer = questions.getAnswers().stream()
                            .filter(answer1 -> Objects.equals(answer1.getId(), answerRequest.getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Anser not found !!!"));
                    modelMapper.map(answerRequest, answer);
                    answer.setQuestions(questions);
                    return answer;
                }).toList();
        questions.setAnswers(listAnswer);
        this.modelMapper.map(request, questions);
        questionRepository.save(questions);
    }

    private Questions saveNewQuestion(QuestionDTO request) {
        Questions questions = new Questions();
        List<Answer> newAnswer = request.getAnswers().stream().map(answerRequest -> {
            Answer answer = new Answer();
            modelMapper.map(answerRequest, answer);
            answer.setQuestions(questions);
            return answer;
        }).toList();
        modelMapper.map(request, questions);
        questions.setAnswers(newAnswer);
        return questionRepository.save(questions);
    }

    public QuestionDTO listById(long id) {
        return this.modelMapper.map(questionRepository.findById(id), QuestionDTO.class);
    }

    public List<QuestionDTO> listAll() {
        return questionRepository.findAll().stream()
                .map(question -> this.modelMapper.map(question, QuestionDTO.class))
                .toList();
    }

    public Page<QuestionDTO> filter(String type, int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC));
        try {
            List<QuestionDTO> questionRespons =
                    questionRepository.findAll(QuestionSpecification.filter(type), pageable).stream()
                            .map(questions -> this.modelMapper.map(questions, QuestionDTO.class))
                            .toList();
            log.debug("Product found !!!!");
            return new PageImpl<>(questionRespons, Pageable.unpaged(), questionRespons.size());
        } catch (IllegalStateException exception) {
            log.error("Product not found !!!!");
            return null;
        }
    }

}
