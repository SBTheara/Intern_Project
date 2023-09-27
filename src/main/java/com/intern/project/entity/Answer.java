package com.intern.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "answer_sequence_generator",sequenceName = "answer_seq",allocationSize = 1)
public class Answer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "answer_sequence_generator")
    private Long id;
    private String type;
    private boolean isActive;
    private boolean isCorrect;
    private String level;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private Questions questions;

}
