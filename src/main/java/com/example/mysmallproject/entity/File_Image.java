package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "filedata",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class File_Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(name = "name")
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private String filepath;

}
