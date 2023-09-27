package com.intern.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "questions_sequence_generator",sequenceName = "questions_seq",allocationSize = 1)
public class Questions extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "questions_sequence_generator")
    private Long id;
    private String type;
    private boolean isActive;
    private String level;
    private int score;
    private String content;
    @OneToMany(mappedBy = "questions",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Answer> answers;
}
