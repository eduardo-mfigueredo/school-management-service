package com.schoolmanagement.poc.stubs;

import com.schoolmanagement.poc.model.request.GradeRequest;
import com.schoolmanagement.poc.model.response.AverageByActivityResponse;
import com.schoolmanagement.poc.model.response.AverageByStudentAndActivityResponse;
import com.schoolmanagement.poc.model.response.AverageByStudentResponse;
import com.schoolmanagement.poc.model.response.GradeResponse;
import com.schoolmanagement.poc.repository.entities.GradeEntity;

import java.time.ZonedDateTime;

public class GradeStubs {

    public static GradeEntity createGradeEntity() {
        return GradeEntity.builder()
                .id("1")
                .studentId("1")
                .grade(10.0)
                .activityId("1")
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static GradeEntity createGradeEntity2() {
        return GradeEntity.builder()
                .id("2")
                .studentId("2")
                .grade(8.0)
                .activityId("1")
                .createdAt("2021-09-01T00:00:00Z")
                .updatedAt("2021-09-01T00:00:00Z")
                .build();
    }

    public static GradeEntity createGradeEntity3() {
        return GradeEntity.builder()
                .id("3")
                .studentId("3")
                .grade(6.0)
                .activityId("1")
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static GradeRequest createGradeRequest() {
        return GradeRequest.builder()
                .studentId("1")
                .grade(10.0)
                .activityId("1")
                .build();
    }

    public static GradeResponse createGradeResponse() {
        return GradeResponse.builder()
                .id("1")
                .studentId("1")
                .grade(10.0)
                .activityId("1")
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static AverageByStudentAndActivityResponse createAverageByStudentAndActivityResponse() {
        return AverageByStudentAndActivityResponse.builder()
                .success(true)
                .message("Average grade retrieved successfully")
                .studentId("71498738947292")
                .activityId("1234567890")
                .average(8.5)
                .build();
    }

    public static AverageByStudentResponse createAverageByStudentResponse() {
        return AverageByStudentResponse.builder()
                .success(true)
                .message("Average grade retrieved successfully")
                .studentId("71498738947292")
                .average(8.5)
                .build();
    }

    public static AverageByActivityResponse createAverageByActivityResponse() {
        return AverageByActivityResponse.builder()
                .success(true)
                .message("Average grade retrieved successfully")
                .activityId("1234567890")
                .average(8.5)
                .build();
    }

}