package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.repository.entities.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<StudentEntity, String>, StudentCustomRepository {
}