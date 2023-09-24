package com.intern.project.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
public class AnswerResponseDTO {
    private long id;
    private String type;
    private boolean isActive;
    private boolean isCorrect;
    private String level;
    private String content;
    private int questionId;
}
