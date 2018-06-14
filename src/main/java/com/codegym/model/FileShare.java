package com.codegym.model;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileShare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private boolean isShare;
    private String fileUrl;

    public FileShare() {
    }

    public FileShare(String name, String description, boolean isShare, String fileUrl) {
        this.name = name;
        this.description = description;
        this.isShare = isShare;
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
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

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        this.isShare = share;
    }
}
