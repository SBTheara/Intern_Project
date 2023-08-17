package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "filedata",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(name = "name")
    private String name;
    private String type;
    private String filepath;

}
