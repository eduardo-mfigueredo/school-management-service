package com.schoolmanagement.poc.repository;

import com.schoolmanagement.poc.repository.entities.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<StudentEntity> findStudentBySearchValue(String searchValue) {

        Pattern regexDocumentNumber =
                Pattern.compile(searchValue.replaceAll("[^a-zA-Z0-9]", ""), Pattern.CASE_INSENSITIVE);

        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("documentNumber").regex(regexDocumentNumber),
                        Criteria.where("phone").regex(searchValue),
                        Criteria.where("email").regex(searchValue)));

        return mongoTemplate.find(query, StudentEntity.class).stream().findFirst();
    }

}