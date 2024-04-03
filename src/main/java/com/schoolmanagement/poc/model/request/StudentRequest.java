package com.schoolmanagement.poc.model.request;

import com.schoolmanagement.poc.repository.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentRequest {

    @NotNull
    private String documentNumber;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phone;

    public static StudentEntity convertToEntity(StudentRequest studentRequest) {
        return StudentEntity.builder()
                .documentNumber(studentRequest.getDocumentNumber())
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .phone(studentRequest.getPhone())
                .active(true)
                .createdAt(ZonedDateTime.now().toString())
                .updatedAt(ZonedDateTime.now().toString())
                .build();
    }

}