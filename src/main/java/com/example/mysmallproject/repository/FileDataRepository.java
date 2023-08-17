package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String filename);
}
