package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.FileData;
import com.example.mysmallproject.repository.FileDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileDataService {
    @Autowired
    private FileDataRepository fileDataRepository;
    public FileData uploadFile(MultipartFile file) throws IOException {
        String randomname = UUID.randomUUID().toString();
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String Filename=randomname+file.getOriginalFilename();
        String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString()+"\\images\\";
        String s = currentDirectoryName+Filename;
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(Filename)
                .type(file.getContentType())
                .filepath(s).build()
        );
        file.transferTo(new File(s));
        return fileData;
    }
    public byte[] downloadImage(String filename) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(filename);
        String filepath = fileData.get().getFilepath();
        byte[] image = Files.readAllBytes(new File(filepath).toPath());
        return image;
    }


}
