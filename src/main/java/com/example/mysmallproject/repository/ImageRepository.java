package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findByName(String filename);
    Optional<Image> deleteFile_ImagesByName(String filename);
}
