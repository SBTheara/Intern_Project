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
//    private MultipartFile filed;
//    private MultipartFile filenull;
//    private byte[] image;
//    public MultipartFile post(MultipartFile file) throws IOException {
//        image=file.getBytes();
//    }
    public File_Image uploadFile(MultipartFile file) throws IOException {
        String random_name = UUID.randomUUID().toString();
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String Filename=random_name+file.getOriginalFilename();
        String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString()+"\\images\\";
        String s = currentDirectoryName+Filename;
        File_Image fileImage = fileDataRepository.save(File_Image.builder()
                .name(Filename)
                .type(file.getContentType())
                .filepath(s).build()
        );
        file.transferTo(new File(s));
        return fileImage;
    }
    public void Deletefile(String filename) throws IOException {
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString()+"\\images\\"+filename;
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
