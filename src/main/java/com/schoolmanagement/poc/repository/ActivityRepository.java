package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.repository.entities.ActivityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<ActivityEntity, String> {
}
