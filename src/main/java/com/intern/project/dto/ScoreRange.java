package com.intern.project.dto;

import lombok.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRange {
    private int minScore;
    private int maxScore;
}
