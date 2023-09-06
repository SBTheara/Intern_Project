package com.intern.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id",columnDefinition = "bigint(20)")
    private long id;
    private String name;
    private String detail;
}
