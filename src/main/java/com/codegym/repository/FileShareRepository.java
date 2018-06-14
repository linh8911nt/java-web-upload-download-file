package com.codegym.repository;

import com.codegym.model.FileShare;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileShareRepository extends PagingAndSortingRepository<FileShare, Long> {
}
