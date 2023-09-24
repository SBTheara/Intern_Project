package com.intern.project.dto;

import com.intern.project.entity.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionResponse {
    private long id;
    private String type;
    private boolean isActive;
    private String level;
    private int score;
    private String content;
    private List<AnswerResponseDTO> answers;
}
