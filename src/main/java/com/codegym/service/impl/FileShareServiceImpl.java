package com.codegym.service.impl;

import com.codegym.model.FileShare;
import com.codegym.repository.FileShareRepository;
import com.codegym.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;

public class FileShareServiceImpl implements FileShareService {

    @Autowired
    private FileShareRepository fileShareRepository;

    @Override
    public Iterable<FileShare> findAll() {
        return fileShareRepository.findAll();
    }

    @Override
    public FileShare findById(Long id) {
        return fileShareRepository.findOne(id);
    }

    @Override
    public void save(FileShare fileShare) {
        fileShareRepository.save(fileShare);
    }

    @Override
    public void delete(Long id) {
        fileShareRepository.delete(id);
    }
}
