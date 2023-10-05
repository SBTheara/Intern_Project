package com.intern.project.service;

import com.intern.project.dto.AnswerDTO;
import com.intern.project.dto.PageResponse;
import com.intern.project.dto.PaginateRequest;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.entity.Questions;
import com.intern.project.repository.QuestionRepository;
import com.intern.project.utils.PaginationRequestUtil;
import com.intern.project.utils.QuestionSpecification;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
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
  private final ModelMapper modelMapper;

  private static final List<String> SORTABLE_FIELD = List.of("type", "level");
  private static final List<String> SCORE_RANGE = List.of("10-50", "50-100");

  public QuestionDTO save(QuestionDTO request, Long id) {
    Questions question = new Questions();
    boolean isNewQuestion = Objects.requireNonNullElse(id, 0L) <= 0L;
    if (isNewQuestion) {
      modelMapper.map(request, question);
    } else {
      question = getQuestion(id);
    }
    createOrUpdateAnswer(question, request);
    this.modelMapper.map(request, question);
    return this.modelMapper.map(questionRepository.save(question), QuestionDTO.class);
  }

  private Questions getQuestion(Long id) {
    return this.questionRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("Not found for this question !! "));
  }

  private void createOrUpdateAnswer(Questions questions, QuestionDTO request) {
    AtomicBoolean isNewAnswer = new AtomicBoolean(true);
    List<Answer> listAnswer =
        request.getAnswers().stream()
            .map(
                answerRequest -> {
                  isNewAnswer.set(Objects.requireNonNullElse(answerRequest.getId(), 0L) <= 0L);
                  if (isNewAnswer.get()) {
                    return this.prepareNewAnswer(questions, answerRequest);
                  } else {
                    return this.prepareUpdateAnswer(questions, answerRequest);
                  }
                })
            .toList();
    questions.setAnswers(listAnswer);
  }

  private Answer prepareUpdateAnswer(Questions questions, AnswerDTO answerRequest) {
    Answer answer =
        questions.getAnswers().stream()
            .filter(answer1 -> Objects.equals(answer1.getId(), answerRequest.getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Anser not found !!!"));
    modelMapper.map(answerRequest, answer);
    answer.setQuestions(questions);
    return answer;
  }

  private Answer prepareNewAnswer(Questions questions, AnswerDTO answerRequest) {
    Answer answer = new Answer();
    modelMapper.map(answerRequest, answer);
    answer.setQuestions(questions);
    return answer;
  }

  public QuestionDTO listById(long id) {
    return this.modelMapper.map(questionRepository.findById(id), QuestionDTO.class);
  }

  public List<QuestionDTO> listAll() {
    return questionRepository.findAll().stream()
        .map(question -> this.modelMapper.map(question, QuestionDTO.class))
        .toList();
  }

  public Page<QuestionDTO> filter(
      PaginateRequest request, String sortBy, String type, String level, String scoreRange) {
    PaginationRequestUtil.validateRequest(request, SORTABLE_FIELD, SCORE_RANGE, scoreRange);
    Pageable pageable =
        PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.by(Sort.Direction.fromString(request.getDirection()), sortBy));
    try {
      List<QuestionDTO> questionResponse =
          questionRepository
              .findAll(
                  QuestionSpecification.filterType(type)
                      .and(QuestionSpecification.filterLevel(level))
                      .and(QuestionSpecification.filterScore(scoreRange)),
                  pageable)
              .stream()
              .map(questions -> this.modelMapper.map(questions, QuestionDTO.class))
              .toList();
      log.debug("Product found !!!!");
      return new PageImpl<>(questionResponse);
    } catch (IllegalStateException exception) {
      log.error("Product not found !!!!");
      return null;
    }
  }

  public Page<Answer> getAnswerByQuestionId(PaginateRequest pageRequest, Long id) {
    Pageable pageable =
        PageRequest.of(
            pageRequest.getPage(),
            pageRequest.getSize(),
            Sort.Direction.fromString(pageRequest.getDirection()),
            pageRequest.getSortBy());
    Questions questions =
        questionRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("question not found !!"));
    List<Answer> answerList = questions.getAnswers();
    return new PageImpl<>(answerList, pageable, answerList.size());
  }
}
