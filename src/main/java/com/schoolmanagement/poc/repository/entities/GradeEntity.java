package com.schoolmanagement.poc.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "grades")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GradeEntity {

    private String id;
    private String activityId;
    private String studentId;
    private Double grade;
    private String createdAt;
    private String updatedAt;

}