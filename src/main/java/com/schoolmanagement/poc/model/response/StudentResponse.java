package com.schoolmanagement.poc.model.response;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StudentResponse extends ResponseModel {

    private String id;
    private String documentNumber;
    private String name;
    private String email;
    private String phone;
    private String createdAt;
    private String updatedAt;
    private boolean active;

    public StudentResponse(boolean success, String message) {
        super(success, message);
    }

    public static StudentResponse convertToResponseModel(StudentEntity studentEntity) {
        return StudentResponse.builder()
            .success(true)
            .message("Successfully parsed")
            .id(studentEntity.getId())
            .documentNumber(studentEntity.getDocumentNumber())
            .name(studentEntity.getName())
            .email(studentEntity.getEmail())
            .phone(studentEntity.getPhone())
            .createdAt(studentEntity.getCreatedAt())
            .updatedAt(studentEntity.getUpdatedAt())
            .active(studentEntity.isActive())
            .build();
    }

}
