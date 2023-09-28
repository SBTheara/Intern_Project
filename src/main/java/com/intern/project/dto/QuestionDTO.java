package com.intern.project.dto;

import com.intern.project.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionDTO{
    private Long id=0L;
    private String type;
    private boolean isActive;
    private String level;
    private int score;
    private String content;
    private List<AnswerDTO> answers;
}
