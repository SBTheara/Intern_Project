package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Entity
@Table(name = "filedata")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
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
