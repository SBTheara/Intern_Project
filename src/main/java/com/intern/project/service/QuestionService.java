package com.intern.project.service;

import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Questions;
import com.intern.project.repository.AnswerRepository;
import com.intern.project.repository.QuestionRepository;
import com.intern.project.utils.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public QuestionDTO save(QuestionDTO request) {
        Questions questions = new Questions();
        if(validQuestion(request)){
            questions.setAnswers(null);
            questions = createNewQuestion(questions,request);
            questionRepository.save(questions);
        }
        return this.modelMapper.map(questions, QuestionDTO.class);
    }

    private Questions createNewQuestion(Questions questions, QuestionDTO request) {
        questions.setAnswers(null);
        modelMapper.map(request,questions);
        return questionRepository.save(questions);
    }

    private boolean validQuestion(QuestionDTO request) {
        return request.getId()==null;
    }

    private List<Answer> getNewAnswer(Questions questions, QuestionDTO request) {
        return request.getAnswers().stream()
                .filter(answer -> answer.getId() !=null)
                .map(ans -> this.modelMapper.map(ans, Answer.class))
                .toList();
    }

    private void updateQuestionOrAnswer(Questions questions, QuestionDTO request) {
        if (request.getAnswers().stream().anyMatch(req -> req.getId() ==null)) {
            List<Answer> listans = new ArrayList<>(this.getNewAnswer(questions, request));
            listans.forEach(answer -> answer.setQuestions(questions));
            answerRepository.saveAll(listans);
        }
        request.getAnswers().stream()
                .filter(answerDTO ->!Objects.isNull(answerDTO.getId()))
                .forEach(
                        update -> {
                            Answer answer =
                                    questions.getAnswers().stream()
                                            .filter(answer1 -> Objects.equals(answer1.getId(), update.getId()))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("Anser not found !!!"));
                            modelMapper.map(update, answer);
                            answer.setQuestions(questions);
                            answerRepository.save(answer);
                        });
    }

    private Questions getQuestion(QuestionDTO request) {
        Questions questions = new Questions();
        if(request.getId()!=null&&request.getId()>0L) {
            return this.questionRepository
                    .findById(request.getId())
                    .filter(question -> question.getId()!=null&&question.getId() > 0L)
                    .orElseThrow(() -> new IllegalStateException("Not found for this question !! "));
        }
        questions.setId(request.getId());
        questions.setAnswers(null);
        modelMapper.map(request,questions);
        return questionRepository.save(questions);
    }

    private long getid(Questions save) {
        return save.getId();
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
