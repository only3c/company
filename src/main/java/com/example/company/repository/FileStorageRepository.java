package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.company.model.FileStorage;


public interface FileStorageRepository extends Repository<FileStorage, Long> {

	FileStorage save(FileStorage fileStorage);
	
	FileStorage findOne(Long id);
	
	List<FileStorage> findAll();
	
	List<FileStorage> findAll(Pageable pageable);
}
