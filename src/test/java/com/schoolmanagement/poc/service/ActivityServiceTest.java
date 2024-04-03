package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.ActivityRequest;
import com.schoolmanagement.poc.model.response.ActivityListResponse;
import com.schoolmanagement.poc.model.response.ActivityResponse;
import com.schoolmanagement.poc.repository.ActivityRepository;
import com.schoolmanagement.poc.repository.entities.ActivityEntity;
import com.schoolmanagement.poc.stubs.ActivityStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Tests for ActivityService")
@SpringBootTest
@ActiveProfiles("test")
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        activityService = new ActivityService(activityRepository);
    }

    @Test
    @DisplayName("Test create method")
    void shouldReceiveAActivityRequestAndCreate() {
        // Given
        ActivityEntity activityEntity = ActivityStubs.createActivityEntity();
        when(activityRepository.save(any(ActivityEntity.class))).thenReturn(activityEntity);

        // Act
        ActivityResponse activityResponse = activityService.create(ActivityStubs.createActivityRequest());

        // Assert
        assertEquals(ActivityStubs.createActivityResponse(), activityResponse);
        assertNotNull(activityResponse);
        assertEquals(activityEntity.getId(), activityResponse.getId());
        assertEquals(activityEntity.getTitle(), activityResponse.getTitle());
        assertEquals(activityEntity.getDescription(), activityResponse.getDescription());
        verify(activityRepository, times(1)).save(any(ActivityEntity.class));
    }

    @Test
    @DisplayName("Test findAll method")
    void shouldReturnAllActivities() {
        // Given
        List<ActivityEntity> activityEntities = Arrays.asList(
                ActivityStubs.createActivityEntity(),
                ActivityStubs.createActivityEntity2(),
                ActivityStubs.createActivityEntity3()
        );
        when(activityRepository.findAll()).thenReturn(activityEntities);

        // Act
        ActivityListResponse activityListResponse = activityService.findAll();

        // Assert
        assertNotNull(activityListResponse);
        assertTrue(activityListResponse.isSuccess());
        assertEquals("Activities retrieved successfully", activityListResponse.getMessage());
        List<ActivityResponse> expectedActivityResponses = activityEntities.stream()
                .map(ActivityResponse::convertToResponseModel)
                .collect(Collectors.toList());
        assertEquals(expectedActivityResponses, activityListResponse.getActivities());
    }

    @Test
    @DisplayName("Test findById method - Activity found")
    void shouldFindActivityByIdWithSuccess() {
        // Given
        ActivityEntity activityEntity = ActivityStubs.createActivityEntity();
        when(activityRepository.findById(anyString())).thenReturn(Optional.of(activityEntity));

        // Act
        ActivityResponse activityResponse = activityService.findById("1");

        // Assert
        assertNotNull(activityResponse);
        assertTrue(activityResponse.isSuccess());
        assertEquals(activityEntity.getId(), activityResponse.getId());
    }

    @Test
    @DisplayName("Test findById method - Activity not found")
    void shouldFindActivityByIdAndThrowException() {
        // Given
        when(activityRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        ActivityResponse activityResponse = activityService.findById("1");

        // Assert
        assertNotNull(activityResponse);
        assertFalse(activityResponse.isSuccess());
        assertEquals("Activity not found", activityResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Activity found")
    void shouldUpdateSuccessfullyAnActivity() {
        // Given
        ActivityRequest activityRequest = ActivityStubs.createActivityRequest();
        ActivityEntity activityEntity = ActivityStubs.createActivityEntity();
        when(activityRepository.findById("1")).thenReturn(Optional.of(activityEntity));
        when(activityRepository.save(any(ActivityEntity.class))).thenReturn(activityEntity);

        // Act
        ActivityResponse activityResponse = activityService.update(activityRequest, "1");
        // Assert

        assertNotNull(activityResponse);
        assertTrue(activityResponse.isSuccess());
        assertEquals("Activity updated successfully", activityResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Activity not found")
    void shouldTryToUpdateAnActivityAndThrowANotFoundException() {
        // Given
        String id = "1";
        when(activityRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ActivityResponse activityResponse = activityService.update(ActivityStubs.createActivityRequest(), id);
        // Assert

        assertNotNull(activityResponse);
        assertFalse(activityResponse.isSuccess());
        assertEquals("Activity not found", activityResponse.getMessage());
    }

    @Test
    @DisplayName("Test delete method - Activity found")
    void shouldDeleteAnActivitySuccessfully() {
        // Given
        String id = "1";
        ActivityEntity activityEntity = ActivityStubs.createActivityEntity();
        when(activityRepository.findById(id)).thenReturn(Optional.of(activityEntity));
        when(activityRepository.save(any(ActivityEntity.class))).thenReturn(activityEntity);

        // Act
        ResponseModel responseModel = activityService.delete(id);

        // Assert
        assertNotNull(responseModel);
        assertTrue(responseModel.isSuccess());
        assertEquals("Activity deleted successfully", responseModel.getMessage());
    }

    @Test
    @DisplayName("Test delete method - Activity not found")
    void shouldTryToDeleteAnActivityAndThrowException() {
        // Given
        String id = "1";
        when(activityRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> activityService.delete(id), "Activity not found");
    }



}
