package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.repository.entities.GradeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends MongoRepository<GradeEntity, String> {
    Optional<List<GradeEntity>> findByStudentId(String studentId);

    Optional<List<GradeEntity>> findByStudentIdAndActivityId(String studentId, String activityId);

    Optional<List<GradeEntity>> findByActivityId(String activityId);

}


