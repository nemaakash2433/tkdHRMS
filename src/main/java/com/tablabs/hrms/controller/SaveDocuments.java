package com.tablabs.hrms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class SaveDocuments {


    @GetMapping("/uploadsDocuments")
    public List<String> uploadMultipleFile(@RequestParam(name = "files") List<MultipartFile> files) {
        return files.stream().map(multipartFile -> {
            String originalFilename = multipartFile.getOriginalFilename();
            return originalFilename;
        }).collect(Collectors.toList());
    }
}
