package com.intern.project.controller;

import com.intern.project.dto.QuestionDTO;
import com.intern.project.entity.Product_;
import com.intern.project.entity.Questions;
import com.intern.project.service.QuestionService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping
    public ResponseEntity<QuestionDTO> save (@RequestBody QuestionDTO request){
        return new ResponseEntity<>(questionService.save(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> list (){
        return new ResponseEntity<>(questionService.listAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> listById(@PathVariable long id){
        return new ResponseEntity<>(questionService.listById(id),HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<Page<QuestionDTO>> filter(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(questionService.filter(type,offset,pageSize),HttpStatus.OK);
    }
}
