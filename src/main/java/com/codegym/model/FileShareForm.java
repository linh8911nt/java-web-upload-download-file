package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class FileShareForm {

    private Long id;
    private String name;
    private String description;
    private boolean status;
    private MultipartFile file;
    private String fileUrl;

    public FileShareForm() {
    }

    public FileShareForm(String name, String description, boolean status, MultipartFile file, String fileUrl) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.file = file;
        this.fileUrl = fileUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
