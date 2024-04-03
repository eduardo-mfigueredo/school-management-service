package com.schoolmanagement.poc.controller.v1;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.ActivityRequest;
import com.schoolmanagement.poc.model.response.ActivityListResponse;
import com.schoolmanagement.poc.model.response.ActivityResponse;
import com.schoolmanagement.poc.repository.ActivityRepository;
import com.schoolmanagement.poc.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping
    @ApiOperation(value = "Get all activities", response = ActivityResponse[].class)
    public ResponseEntity<ActivityListResponse> getAllActivities(
            @RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an activity by id", response = ActivityResponse.class)
    public ResponseEntity<ActivityResponse> getActivityById(
             @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(activityService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create an activity", response = ActivityResponse.class)
    public ResponseEntity<ActivityResponse> createActivity(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ActivityRequest activityRequest) {
        return ResponseEntity.ok(activityService.create(activityRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an activity", response = ActivityResponse.class)
    public ResponseEntity<ActivityResponse> updateActivity(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ActivityRequest activityRequest,
            @PathVariable String id) {
        return ResponseEntity.ok(activityService.update(activityRequest, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an activity", response = ResponseModel.class)
    public ResponseEntity<ResponseModel> deleteActivity(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String id) {
        return ResponseEntity.ok(activityService.delete(id));
    }

}