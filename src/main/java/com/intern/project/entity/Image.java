package com.intern.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id",columnDefinition = "bigint(20)")
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private String filePath;
}
