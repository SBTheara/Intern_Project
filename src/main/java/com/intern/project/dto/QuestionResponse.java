package com.intern.project.dto;

import com.intern.project.entity.Answer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionResponse {
    private long id;
    private String name;
    private String type;
    private List<Answer> answers;
}
