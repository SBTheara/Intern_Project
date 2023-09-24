package com.intern.project.controller;

import com.intern.project.dto.QuestionResponse;
import com.intern.project.entity.Questions;
import com.intern.project.service.QuestionService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Questions> save (@RequestBody Questions request){
        return new ResponseEntity<>(questionService.save(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> list (){
        return new ResponseEntity<>(questionService.listAll(), HttpStatus.OK);
    }
}
