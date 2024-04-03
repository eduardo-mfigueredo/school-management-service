package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.ActivityRequest;
import com.schoolmanagement.poc.model.response.ActivityListResponse;
import com.schoolmanagement.poc.model.response.ActivityResponse;
import com.schoolmanagement.poc.repository.ActivityRepository;
import com.schoolmanagement.poc.service.interfaces.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static com.schoolmanagement.poc.model.request.ActivityRequest.convertToEntity;

@Service
@RequiredArgsConstructor
public class ActivityService implements ICrudService<ActivityRequest, ActivityResponse, ActivityListResponse> {

    private final ActivityRepository activityRepository;

    @Override
    public ActivityResponse create(ActivityRequest activityRequest) {
        return ActivityResponse.convertToResponseModel(
                activityRepository.save(convertToEntity(activityRequest)));
    }

    @Override
    public ActivityListResponse findAll() {
        return ActivityListResponse.builder()
                .success(true)
                .message("Activities retrieved successfully")
                .activities(activityRepository.findAll()
                        .stream()
                        .map(ActivityResponse::convertToResponseModel)
                        .collect(Collectors.toList())).build();

    }

    @Override
    public ActivityResponse findById(String id) {
        return activityRepository.findById(id)
                .map(ActivityResponse::convertToResponseModel)
                .orElse(new ActivityResponse(false, "Activity not found"));
    }

    @Override
    public ActivityResponse update(ActivityRequest activityRequest, String id) {
        return activityRepository.findById(id)
                .map(activityEntity -> {
                    activityEntity.setTitle(activityRequest.getTitle());
                    activityEntity.setDescription(activityRequest.getDescription());
                    activityEntity.setUpdatedAt(ZonedDateTime.now().toString());

                    ActivityResponse activityResponse =
                            ActivityResponse.convertToResponseModel(
                                    activityRepository.save(activityEntity));

                    activityResponse.setMessage("Activity updated successfully");
                    return activityResponse;
                })
                .orElse(new ActivityResponse(false, "Activity not found"));
    }

    @Override
    public ResponseModel delete(String id) {
        return activityRepository.findById(id).map(activityEntity -> {
            activityEntity.setActive(false);
            activityRepository.save(activityEntity);
            return ResponseModel.builder().success(true).message("Activity deleted successfully").build();
        }).orElseThrow(() -> new RuntimeException("Activity not found"));
    }

}
