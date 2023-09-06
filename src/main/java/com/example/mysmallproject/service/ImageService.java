package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Image;
import com.example.mysmallproject.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {
  @Autowired private ImageRepository imageRepository;
  private final Path fileStorageLocation;

  @Autowired
  public ImageService(Environment env) {
    this.fileStorageLocation =
        Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
            .toAbsolutePath()
            .normalize();
    try {
      Files.createDirectories(this.fileStorageLocation);
      log.debug("Successfully create the file location directory");
    } catch (Exception ex) {
      log.error("Could not create the directory where the uploaded files will be stored.");
      throw new RuntimeException(
          "Could not create the directory where the uploaded files will be stored.", ex);
    }
  }

  public Image uploadImage(MultipartFile file) throws IOException {
    Path targetLocation = this.fileStorageLocation.toAbsolutePath(); // get current directory
    String Filename =
        UUID.randomUUID()
            + file.getOriginalFilename(); // get random characters and combine with the original
    // filename
    String currentFilename = targetLocation + "\\" + Filename; // get path to upload
    try {
      Image image =
          imageRepository.save(
              Image.builder() // save data to File_Image object
                  .name(Filename)
                  .type(file.getContentType())
                  .filePath(currentFilename)
                  .build());
      file.transferTo(new File(currentFilename));
      log.debug("The image was uploaded successfully to the {}", targetLocation);
      return image;

    } catch (IllegalStateException exception) {
      log.debug("Something went wrong while uploading image !!!");
      return null;
    }
    // transfer file to directory

  }

  public void deleteFile(String filename) throws IOException {
    Path targetLocation = this.fileStorageLocation.toAbsolutePath();
    String currentDirectoryName = targetLocation + "\\" + filename;
    Path getpath = Paths.get(currentDirectoryName);
    try {
      Files.delete(getpath);
      imageRepository.deleteByName(filename);
      log.debug("Successfully delete the image !!!");
    } catch (IllegalStateException exception) {
      log.error("Failed to delete image !!!");
    }
  }

  public byte[] downloadImage(String filename) throws IOException {
    try {
      Optional<Image> fileData = imageRepository.findByName(filename);
      String filepath = fileData.get().getFilePath();
      byte[] image = Files.readAllBytes(new File(filepath).toPath());
      log.debug("Successfully download image !!!");
      return image;
    } catch (IllegalStateException exception) {
      log.error("Field to download image !!! ");
      return null;
    }
  }
}
