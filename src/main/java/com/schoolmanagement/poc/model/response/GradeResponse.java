package com.schoolmanagement.poc.model.response;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.repository.entities.GradeEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GradeResponse extends ResponseModel {

    private String id;
    private String activityId;
    private String studentId;
    private Double grade;
    private String createdAt;
    private String updatedAt;

    public static GradeResponse convertToResponseModel(GradeEntity gradeEntity) {
        return GradeResponse.builder()
                .success(true)
                .message("Successfully parsed")
                .id(gradeEntity.getId())
                .activityId(gradeEntity.getActivityId())
                .studentId(gradeEntity.getStudentId())
                .grade(gradeEntity.getGrade())
                .createdAt(gradeEntity.getCreatedAt())
                .updatedAt(gradeEntity.getUpdatedAt())
                .build();
    }
}
