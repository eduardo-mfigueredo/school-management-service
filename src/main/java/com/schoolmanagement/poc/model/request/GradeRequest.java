package com.schoolmanagement.poc.model.request;

import com.schoolmanagement.poc.repository.entities.GradeEntity;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GradeRequest {

    @NotNull
    private String activityId;
    @NotNull
    private String studentId;
    @NotNull
    private Double grade;

    public static GradeEntity convertToEntity(GradeRequest requestModel) {
        return GradeEntity.builder()
            .activityId(requestModel.getActivityId())
            .studentId(requestModel.getStudentId())
            .grade(requestModel.getGrade())
            .createdAt(ZonedDateTime.now().toString())
            .updatedAt(ZonedDateTime.now().toString())
            .build();
    }
}