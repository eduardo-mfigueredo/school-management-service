package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.enums.Roles;
import com.schoolmanagement.poc.repository.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByRole(Roles role);

}
