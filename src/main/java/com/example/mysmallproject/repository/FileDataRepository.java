package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.File_Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<File_Image,Integer> {
    Optional<File_Image> findByName(String filename);
    Optional<File_Image> deleteFile_ImagesByName(String filename);
}
