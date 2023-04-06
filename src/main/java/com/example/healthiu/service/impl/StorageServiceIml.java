package com.example.healthiu.service.impl;

import com.example.healthiu.service.StorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

@Service
public class StorageServiceIml implements StorageService {
    private final Environment environment;
    private Storage storage;
    private final static String bucketName = "healthiu-base.appspot.com";

    @Autowired
    public StorageServiceIml(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void initializeFirebase() throws Exception {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/service_account.json");
        this.storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build().getService();

    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);
        BlobId blobId = BlobId.of(bucketName, "user/" + objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
        storage.create(blobInfo, Files.readAllBytes(filePath));
        return getFilePath(objectName);
    }

    @Override
    public void deleteFile(String filePath) {
        storage.delete(BlobId.of(bucketName, "user/" + getFileName(filePath)));
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    private String getFilePath(String fileName) {
        return "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/user%2F" + fileName + "?alt=media";
    }

    private String getFileName(String filePath) {
        String fileName = filePath;
        fileName = fileName.replace("https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/user%2F", "");
        fileName = fileName.replace("?alt=media", "");
        return fileName;
    }
}