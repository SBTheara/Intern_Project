package com.intern.project.controller;

import com.intern.project.entity.Answer;
import com.intern.project.service.AnswerService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Answer>> listAll(){
        return new ResponseEntity<>(answerService.listAll(), HttpStatus.OK);
    }
}
