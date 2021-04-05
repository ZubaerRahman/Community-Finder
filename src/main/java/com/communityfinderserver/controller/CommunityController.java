package com.communityfinderserver.controller;

import com.communityfinderserver.model.Community;
import com.communityfinderserver.repository.CommunityRepository;
import com.communityfinderserver.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/community")
    public Community saveCommunity(@RequestBody Community community) {
        return communityRepository.save(community);
    }

    @GetMapping("/community/{id}")
    public Community getCommunity(@PathVariable String id) {
        return communityRepository.getCommunityById(id);
    }

    @DeleteMapping("/community/{id}")
    public String deleteCommunity(@PathVariable String id) {
        return communityRepository.delete(id);
    }

    @PutMapping("/community/{id}")
    public String updateCommunity(@PathVariable String id, @RequestBody Community community) {
        return communityRepository.update(id, community);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return fileStorageService.uploadFile(file);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        var data = fileStorageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(fileStorageService.deleteFile(fileName), HttpStatus.OK);
    }

}
