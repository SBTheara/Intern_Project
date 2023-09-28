package com.intern.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.project.entity.Questions;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AnswerDTO {
    private Long id;
    private String type;
    private boolean isActive;
    private boolean isCorrect;
    private String level;
    private String content;
}
