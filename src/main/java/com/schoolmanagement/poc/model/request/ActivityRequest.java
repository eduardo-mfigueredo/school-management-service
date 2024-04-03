package com.schoolmanagement.poc.model.request;

import com.schoolmanagement.poc.repository.entities.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivityRequest {

    @NotNull
    private String title;
    @NotNull
    private String description;

    public static ActivityEntity convertToEntity(ActivityRequest activityRequest) {
        return ActivityEntity.builder()
            .title(activityRequest.getTitle())
            .description(activityRequest.getDescription())
            .active(true)
            .build();
    }

}
