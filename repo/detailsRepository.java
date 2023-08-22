package com.example.demo.repo;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.detail.details;

public interface detailsRepository extends MongoRepository<details, LocalDateTime> {

}
