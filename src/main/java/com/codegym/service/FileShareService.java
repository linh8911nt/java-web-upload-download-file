package com.codegym.service;

import com.codegym.model.FileShare;

public interface FileShareService {

    Iterable<FileShare> findAll();

    FileShare findById(Long id);

    void save(FileShare fileShare);

    void delete(Long id);
}
