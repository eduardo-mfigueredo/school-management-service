package com.schoolmanagement.poc.model.response;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.repository.entities.ActivityEntity;
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
public class ActivityResponse extends ResponseModel {

    private String id;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private boolean active;

    public ActivityResponse(boolean success, String message) {
        super(success, message);
    }

    public static ActivityResponse convertToResponseModel(ActivityEntity activityEntity) {
        return ActivityResponse.builder()
                .success(true)
                .message("Successfully parsed")
                .id(activityEntity.getId())
                .title(activityEntity.getTitle())
                .description(activityEntity.getDescription())
                .createdAt(activityEntity.getCreatedAt())
                .updatedAt(activityEntity.getUpdatedAt())
                .active(activityEntity.isActive())
                .build();
    }

}