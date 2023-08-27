package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.File_Image;
import com.example.mysmallproject.repository.FileDataRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.RecursiveTask;

@Service
public class FileDataService {
    @Autowired
    private FileDataRepository fileDataRepository;
    public File_Image uploadFile(MultipartFile file) throws IOException {
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");//get current directory
        String Filename=UUID.randomUUID()+file.getOriginalFilename();// get random characters and combine with the original filename
        String currentFilename = currentDirectoryPath.toAbsolutePath()+"\\images\\"+Filename;//get path to upload
        File_Image fileImage = fileDataRepository.save(File_Image.builder() //save data to File_Image object
                .name(Filename)
                .type(file.getContentType())
                .filepath(currentFilename).build()
        );
        file.transferTo(new File(currentFilename));//transfer file to directory
        return fileImage;
    }
    public void deletefile(String filename) throws IOException {
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String currentDirectoryName = currentDirectoryPath.toAbsolutePath()+"\\images\\"+filename;
        Path getpath = Paths.get(currentDirectoryName);
        Files.delete(getpath);
        fileDataRepository.deleteFile_ImagesByName(filename);
    }
    public byte[] downloadImage(String filename) throws IOException {
        Optional<File_Image> fileData = fileDataRepository.findByName(filename);
        String filepath = fileData.get().getFilepath();
        byte[] image = Files.readAllBytes(new File(filepath).toPath());
        return image;
    }


}
