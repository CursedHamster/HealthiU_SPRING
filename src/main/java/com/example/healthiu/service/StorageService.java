package com.example.healthiu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    void initializeFirebase() throws Exception;

    String uploadFile(MultipartFile multipartFile) throws Exception;

    void deleteFile(String filePath) throws IOException;
}