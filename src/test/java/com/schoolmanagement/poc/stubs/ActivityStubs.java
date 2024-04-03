package com.schoolmanagement.poc.stubs;

import com.schoolmanagement.poc.model.request.ActivityRequest;
import com.schoolmanagement.poc.model.response.ActivityResponse;
import com.schoolmanagement.poc.repository.entities.ActivityEntity;

public class ActivityStubs {

    public static ActivityRequest createActivityRequest() {
        return ActivityRequest.builder()
                .title("Activity 1")
                .description("Description 1")
                .build();
    }

    public static ActivityEntity createActivityEntity() {
        return ActivityEntity.builder()
                .id("1")
                .title("Activity 1")
                .description("Description 1")
                .createdAt("2021-09-01T00:00:00Z")
                .updatedAt("2021-09-01T00:00:00Z")
                .active(true)
                .build();
    }

    public static ActivityEntity createActivityEntity2() {
        return ActivityEntity.builder()
                .id("2")
                .title("Activity 2")
                .description("Description 2")
                .createdAt("2021-19-01T00:00:00Z")
                .updatedAt("2021-19-01T00:00:00Z")
                .active(true)
                .build();
    }

    public static ActivityEntity createActivityEntity3() {
        return ActivityEntity.builder()
                .id("3")
                .title("Activity 3")
                .description("Description 3")
                .createdAt("2021-29-01T00:00:00Z")
                .updatedAt("2021-29-01T00:00:00Z")
                .active(true)
                .build();
    }

    public static ActivityResponse createActivityResponse() {
        return ActivityResponse.builder()
                .success(true)
                .message("Successfully parsed")
                .id("1")
                .title("Activity 1")
                .description("Description 1")
                .createdAt("2021-09-01T00:00:00Z")
                .updatedAt("2021-09-01T00:00:00Z")
                .active(true)
                .build();
    }

}
