package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.FileData;
import com.example.mysmallproject.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/products/images")
public class FileImageController {
    @Autowired
    private FileDataService fileDataService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(name = "image") MultipartFile file) throws IOException {
        FileData uploadFile =  fileDataService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }
    @GetMapping("/get/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "filename") String filename) throws IOException {
        byte[] img = fileDataService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(img);
    }
}
