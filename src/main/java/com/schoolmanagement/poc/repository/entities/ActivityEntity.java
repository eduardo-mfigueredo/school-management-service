package com.schoolmanagement.poc.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activities")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivityEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private boolean active;

}
