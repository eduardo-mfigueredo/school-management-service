package com.schoolmanagement.poc.stubs;

import com.schoolmanagement.poc.model.request.StudentRequest;
import com.schoolmanagement.poc.model.response.AverageByActivityResponse;
import com.schoolmanagement.poc.model.response.AverageByStudentAndActivityResponse;
import com.schoolmanagement.poc.model.response.AverageByStudentResponse;
import com.schoolmanagement.poc.model.response.StudentResponse;
import com.schoolmanagement.poc.repository.entities.StudentEntity;

public class StudentStubs {

    public static StudentRequest createStudentRequest() {
        return StudentRequest.builder()
                .name("Joao da Silva")
                .email("joaodasilva@gmail.com")
                .phone("11999999999")
                .documentNumber("02223388093")
                .build();
    }

    public static StudentEntity createStudentEntity() {
        return StudentEntity.builder()
                .id("71498738947292")
                .name("Joao da Silva")
                .email("joaodasilva@gmail.com")
                .phone("11999999999")
                .documentNumber("02223388093")
                .active(true)
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static StudentEntity createStudentEntity2() {
        return StudentEntity.builder()
                .id("133794837098")
                .name("Maria Pereira")
                .email("maria@gmail.com")
                .phone("11999988888")
                .documentNumber("023243984301")
                .active(true)
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static StudentEntity createStudentEntity3() {
        return StudentEntity.builder()
                .id("9748927398702")
                .name("José Santos")
                .email("jose@gmail.com")
                .phone("11999977777")
                .documentNumber("02324398901")
                .active(true)
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static StudentResponse createStudentResponse() {
        return StudentResponse.builder()
                .success(true)
                .message("Successfully parsed")
                .id("71498738947292")
                .name("Joao da Silva")
                .email("joaodasilva@gmail.com")
                .phone("11999999999")
                .documentNumber("02223388093")
                .active(true)
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }

    public static StudentEntity createInactiveStudentEntity() {
        return StudentEntity.builder()
                .id("9748927398702")
                .name("José Santos")
                .email("jose@gmail.com")
                .phone("11999977777")
                .documentNumber("02324398901")
                .active(false)
                .createdAt("2021-08-01T00:00:00Z")
                .updatedAt("2021-08-01T00:00:00Z")
                .build();
    }
}