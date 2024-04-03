package com.schoolmanagement.poc.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentEntity {

    @Id
    private String id;
    private String documentNumber;
    private String name;
    private String email;
    private String phone;
    private String createdAt;
    private String updatedAt;
    private boolean active;

}
