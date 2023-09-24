package com.intern.project.controller;

import com.intern.project.dto.ProductDTO;
import com.intern.project.dto.QuestionResponse;
import com.intern.project.entity.Product_;
import com.intern.project.entity.Questions;
import com.intern.project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> listById(@PathVariable long id){
        return new ResponseEntity<>(questionService.listById(id),HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<Page<QuestionResponse>> filter(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(questionService.filter(type,offset,pageSize),HttpStatus.OK);
    }
}
