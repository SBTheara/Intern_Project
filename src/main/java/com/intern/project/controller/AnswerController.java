package com.intern.project.controller;

import com.intern.project.dto.AnswerDTO;
import com.intern.project.dto.PaginateRequest;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Answer;
import com.intern.project.exception.PaginateResponse;
import com.intern.project.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    @PostMapping
    public ResponseEntity<Answer> save (@RequestBody Answer answer){
        return new ResponseEntity<>(answerService.save(answer),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<PaginateResponse<AnswerDTO>> listAll(
            @RequestParam(defaultValue = StringUtils.EMPTY) String correct,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "type") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction
    ){
        PaginateRequest paginateRequest =
                PaginateRequest.builder()
                        .page(page-1)
                        .size(size)
                        .sortBy(sortBy)
                        .direction(direction)
                        .build();
        Page<AnswerDTO> answerResponse =
                answerService.filter(paginateRequest, sortBy,correct);
        return new ResponseEntity<>(
                new PaginateResponse<>(
                        answerResponse.getTotalElements(), page, size, answerResponse.getContent()),
                HttpStatus.OK);
    }
}
