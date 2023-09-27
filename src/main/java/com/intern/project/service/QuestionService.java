package com.intern.project.service;

import com.intern.project.dto.AnswerDTO;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Questions;
import com.intern.project.repository.AnswerRepository;
import com.intern.project.repository.QuestionRepository;
import com.intern.project.utils.QuestionSpecification;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final AnswerService answerService;
  private final ModelMapper modelMapper;

  public QuestionDTO save(QuestionDTO request) {
    Questions questions = this.getQuestion(request);
    this.updateQuestionOrAnswer(questions, request);

    List<Answer> newAnswers = this.getNewAnswer(request);
    questions.setAnswers(newAnswers);

    this.questionRepository.save(questions);
    return this.modelMapper.map(questions, QuestionDTO.class);
  }

  private List<Answer> getNewAnswer(QuestionDTO request) {
    return request.getAnswers().stream()
        .filter(answer -> answer.getId() <= 0)
        .map(ans -> this.modelMapper.map(ans, Answer.class))
        .toList();
  }

  private void updateQuestionOrAnswer(Questions questions, QuestionDTO request) {
    request.getAnswers().stream()
        .filter(answerDTO -> answerDTO.getId() > 0)
        .forEach(
            update -> {
              Answer answer =
                  questions.getAnswers().stream()
                      .filter(answer1 -> answer1.getId() == update.getId())
                      .findFirst()
                      .orElseThrow(() -> new IllegalStateException("Anser not found !!!"));
              modelMapper.map(update, answer);
            });
  }

  private Questions getQuestion(QuestionDTO request) {
    if (request.getId() <= 0) {
      return this.modelMapper.map(request, Questions.class);
    } else {
      return this.questionRepository
          .findById(request.getId())
          .filter(questions -> questions.getId() > 0)
          .orElseThrow(() -> new IllegalStateException("Not found for this question !! "));
    }
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
