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
    Questions questions = modelMapper.map(request, Questions.class);
    questions.setType(request.getType());
    questions.setLevel(request.getLevel());
    questions.setScore(request.getScore());
    questions.setContent(request.getContent());
    questions.setActive(request.isActive());
    List<Answer> answersList = new ArrayList<>();
    List<AnswerDTO> list = request.getAnswers();
    questionRepository.save(questions);
    list.forEach(
        answer -> {
          Answer answers = new Answer();
          answers.setType(answer.getType());
          answers.setLevel(answer.getLevel());
          answers.setContent(answer.getContent());
          answers.setActive(answers.isActive());
          answers.setCorrect(answer.isCorrect());
          answers.setQuestions(questions);
          answersList.add(answers);
          answerRepository.save(answers);
        });
    questions.setAnswers(answersList);
    return modelMapper.map(questionRepository.save(questions), QuestionDTO.class);
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
