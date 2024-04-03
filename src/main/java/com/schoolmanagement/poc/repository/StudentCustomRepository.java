package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.repository.entities.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCustomRepository {

    Optional<StudentEntity> findStudentBySearchValue(String searchValue);

}