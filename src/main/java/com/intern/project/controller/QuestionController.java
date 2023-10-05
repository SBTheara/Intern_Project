package com.intern.project.controller;

import com.intern.project.dto.PageResponse;
import com.intern.project.dto.PaginateRequest;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.exception.PaginateResponse;
import com.intern.project.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/questions")
public class QuestionController {
  private final QuestionService questionService;

  @PostMapping(value = "/{id}/answers")
  public ResponseEntity<QuestionDTO> save(
      @RequestBody QuestionDTO request, @PathVariable(required = false) Long id) {
    return new ResponseEntity<>(questionService.save(request, id), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<QuestionDTO>> list() {
    return new ResponseEntity<>(questionService.listAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionDTO> listById(@PathVariable long id) {
    return new ResponseEntity<>(questionService.listById(id), HttpStatus.OK);
  }

  @GetMapping(value = "/filter")
  public ResponseEntity<PaginateResponse<QuestionDTO>> filter(
      @RequestParam(name = "sort-by", required = false, defaultValue = "type") String sortBy,
      @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
      @RequestParam(name = "type", required = false, defaultValue = StringUtils.EMPTY) String type,
      @RequestParam(name = "level", required = false, defaultValue = StringUtils.EMPTY)
          String level,
      @RequestParam(name = "scoreRange", required = false, defaultValue = StringUtils.EMPTY)
          String scoreRange,
      @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
      @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
    PaginateRequest paginateRequest =
        PaginateRequest.builder()
            .page(offset)
            .size(pageSize)
            .sortBy(sortBy)
            .direction(direction)
            .build();
    Page<QuestionDTO> questions =
        questionService.filter(paginateRequest, sortBy, type, level, scoreRange);
    return new ResponseEntity<>(
        new PaginateResponse<>(
            questions.getTotalElements(), offset, pageSize, questions.getContent()),
        HttpStatus.OK);
  }

  @GetMapping("/get-by-question-id")
  public ResponseEntity<PageResponse<Answer>> getAnswerByQuestionId(
      @RequestParam(name = "sort-by", required = false, defaultValue = "type") String sortBy,
      @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
      @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
      @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize,
      @RequestParam Long id) {
    PaginateRequest pageRequest =
        PaginateRequest.builder()
            .page(offset - 1)
            .size(pageSize)
            .sortBy(sortBy)
            .direction(direction)
            .build();
    Page<Answer> answerResponse = questionService.getAnswerByQuestionId(pageRequest, id);
    return new ResponseEntity<>(
        new PageResponse<>(
           offset, pageSize,
           answerResponse.getTotalElements(),
           answerResponse.getContent()),
        HttpStatus.OK);
  }
}

