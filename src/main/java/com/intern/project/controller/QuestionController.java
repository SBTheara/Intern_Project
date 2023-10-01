package com.intern.project.controller;

import com.intern.project.dto.PaginateRequest;
import com.intern.project.dto.QuestionDTO;
import com.intern.project.exception.PaginateResponse;
import com.intern.project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<QuestionDTO> save(@RequestBody QuestionDTO request) {
        return new ResponseEntity<>(questionService.save(request), HttpStatus.CREATED);
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
            @RequestParam(name = "type", required = false,defaultValue = StringUtils.EMPTY) String type,
            @RequestParam(name = "level", required = false,defaultValue = StringUtils.EMPTY) String level,
            @RequestParam(name = "score", required = false,defaultValue = StringUtils.EMPTY) String score,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        PaginateRequest paginateRequest = PaginateRequest.builder()
                .page(offset)
                .size(pageSize)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<QuestionDTO> questions = questionService.filter(paginateRequest, sortBy,type,level,score);
        return new ResponseEntity<>(new PaginateResponse<QuestionDTO>(
                questions.getTotalElements(),
                offset,
                pageSize,
                questions.getContent()),
                HttpStatus.OK);
    }
}
