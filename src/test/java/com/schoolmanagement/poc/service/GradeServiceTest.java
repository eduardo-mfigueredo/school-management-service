package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.request.GradeRequest;
import com.schoolmanagement.poc.model.response.*;
import com.schoolmanagement.poc.repository.GradeRepository;
import com.schoolmanagement.poc.repository.entities.GradeEntity;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
import com.schoolmanagement.poc.stubs.GradeStubs;
import com.schoolmanagement.poc.stubs.StudentStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Tests for GradeService")
@SpringBootTest
@ActiveProfiles("test")
class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gradeService = new GradeService(gradeRepository, studentService);
    }

    @Test
    @DisplayName("Test create method")
    void shouldReceiveAGradeRequestAndCreate() {
        // Given
        GradeEntity gradeEntity = GradeStubs.createGradeEntity();
        when(gradeRepository.save(any(GradeEntity.class))).thenReturn(gradeEntity);

        // Act
        GradeResponse gradeResponse = gradeService.create(GradeStubs.createGradeRequest());
        // Assert
        assertEquals(GradeStubs.createGradeResponse(), gradeResponse);
        assertNotNull(gradeResponse);
        assertEquals(gradeEntity.getId(), gradeResponse.getId());
        assertEquals(gradeEntity.getGrade(), gradeResponse.getGrade());
        assertEquals(gradeEntity.getActivityId(), gradeResponse.getActivityId());
        assertEquals(gradeEntity.getStudentId(), gradeResponse.getStudentId());
        verify(gradeRepository, times(1)).save(any(GradeEntity.class));
    }

    @Test
    @DisplayName("Test findAll method")
    void shouldReturnAllGrades() {
        // Given
        List<GradeEntity> gradeEntities = Arrays.asList(
                GradeStubs.createGradeEntity(),
                GradeStubs.createGradeEntity2(),
                GradeStubs.createGradeEntity3()
        );
        when(gradeRepository.findAll()).thenReturn(gradeEntities);

        // Act
        GradeListResponse gradeListResponse = gradeService.findAll();

        // Assert
        assertNotNull(gradeListResponse);
        assertTrue(gradeListResponse.isSuccess());
        assertEquals("Grades retrieved successfully", gradeListResponse.getMessage());
        List<GradeResponse> expectedGradeResponses = gradeEntities.stream()
                .map(GradeResponse::convertToResponseModel)
                .collect(Collectors.toList());
        assertEquals(expectedGradeResponses, gradeListResponse.getGrades());
    }

    @Test
    @DisplayName("Test findById method - Grade found")
    void shouldFindGradeByIdWithSuccess() {
        // Given
        GradeEntity gradeEntity = GradeStubs.createGradeEntity();
        when(gradeRepository.findById("1")).thenReturn(Optional.of(gradeEntity));

        // Act
        GradeResponse gradeResponse = gradeService.findById("1");

        // Assert
        assertNotNull(gradeResponse);
        assertTrue(gradeResponse.isSuccess());
        assertEquals(gradeEntity.getId(), gradeResponse.getId());
    }

    @Test
    @DisplayName("Test findById method - Grade not found")
    void shouldFindGradeByIdAndThrowException() {
        // Given
        when(gradeRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        GradeResponse gradeResponse = gradeService.findById("1");

        // Assert
        assertNotNull(gradeResponse);
        assertFalse(gradeResponse.isSuccess());
        assertEquals("Grade not found", gradeResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Grade found")
    void shouldUpdateSuccessfullyAGrade() {
        // Given
        GradeEntity gradeEntity = GradeStubs.createGradeEntity();
        GradeRequest gradeRequest = GradeStubs.createGradeRequest();
        when(gradeRepository.findById("1")).thenReturn(Optional.of(gradeEntity));
        when(gradeRepository.save(any(GradeEntity.class))).thenReturn(gradeEntity);

        // Act
        GradeResponse gradeResponse = gradeService.update(gradeRequest, "1");

        // Assert

        assertNotNull(gradeResponse);
        assertTrue(gradeResponse.isSuccess());
        assertEquals("Grade updated successfully", gradeResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Grade not found")
    void shouldTryToUpdateAGradeAndThrowANotFoundException() {
        // Given
        String id = "1";
        when(gradeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        GradeResponse gradeResponse = gradeService.update(GradeStubs.createGradeRequest(), id);
        // Assert


        assertNotNull(gradeResponse);
        assertFalse(gradeResponse.isSuccess());
        assertEquals("Grade not found", gradeResponse.getMessage());
    }

    @Test
    @DisplayName("Test getStudentAverage by activity method - Grades found")
    void shouldGetTheAverageByStudentAndActivitySuccessfully() {
        // Given
        String studentId = "1";
        String activityId = "A1";
        List<GradeEntity> grades = Arrays.asList(
                GradeStubs.createGradeEntity(),
                GradeStubs.createGradeEntity2(),
                GradeStubs.createGradeEntity3()
        );
        when(gradeRepository.findByStudentIdAndActivityId(studentId, activityId)).thenReturn(Optional.of(grades));

        // Act
        AverageByStudentAndActivityResponse response = gradeService.getStudentAverage(studentId, activityId);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Average grade retrieved successfully", response.getMessage());
        assertEquals(studentId, response.getStudentId());
        assertEquals(activityId, response.getActivityId());
        assertEquals(8.0, response.getAverage());
    }

    @Test
    @DisplayName("Test getStudentAverage by activity method - Grades not found")
    void shouldTryToGetStudentAverageByActivityAndThrowException() {
        // Given
        String studentId = "1";
        String activityId = "1";
        when(gradeRepository.findByStudentIdAndActivityId(studentId, activityId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () ->
                gradeService.getStudentAverage(studentId, activityId), HttpStatus.NOT_FOUND.toString());
    }

    @Test
    @DisplayName("Test getStudentAverage method - Grades found")
    void shouldGetStudentAverageForAllGrades() {
        // Given
        String studentId = "1";
        List<GradeEntity> grades = Arrays.asList(
                GradeStubs.createGradeEntity(),
                GradeStubs.createGradeEntity2(),
                GradeStubs.createGradeEntity3()
        );
        when(gradeRepository.findByStudentId(studentId)).thenReturn(Optional.of(grades));

        // Act
        AverageByStudentResponse response = gradeService.getStudentAverage(studentId);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Average grade retrieved successfully", response.getMessage());
        assertEquals(studentId, response.getStudentId());
        assertEquals(8.0, response.getAverage());
    }

    @Test
    @DisplayName("Test getStudentAverage method - Grades not found")
    void shouldTryGetStudentAverageForAllGradesAndThrowException() {
        // Given
        String studentId = "1";
        when(gradeRepository.findByStudentId(studentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () ->
                gradeService.getStudentAverage(studentId), HttpStatus.NOT_FOUND.toString());
    }

    @Test
    @DisplayName("Test getActivityAverage method - Grades found")
    void shouldGetActivityAverageGradesSuccessfully() {
        // Given
        String activityId = "1";
        List<GradeEntity> grades = Arrays.asList(
                GradeStubs.createGradeEntity(),
                GradeStubs.createGradeEntity2(),
                GradeStubs.createGradeEntity3()
        );
        when(gradeRepository.findByActivityId(activityId)).thenReturn(Optional.of(grades));

        // Act
        AverageByActivityResponse response = gradeService.getActivityAverage(activityId);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Average grade retrieved successfully", response.getMessage());
        assertEquals(activityId, response.getActivityId());
        assertEquals(8.0, response.getAverage());
    }

    @Test
    @DisplayName("Test getActivityAverage method - Grades not found")
    void shouldTryGetActivityAverageGradesAndThrowException() {
        // Given
        String activityId = "1";
        when(gradeRepository.findByActivityId(activityId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () ->
                gradeService.getActivityAverage(activityId), HttpStatus.NOT_FOUND.toString());
    }

    @Test
    @DisplayName("Test getGradesByStudent method")
    void shouldGetGradesByStudentSuccessfully() {
        // Given
        String searchValue = "Joao da Silva";
        StudentEntity studentEntity = StudentStubs.createStudentEntity();
        when(studentService.findBySearchValue(searchValue)).thenReturn(studentEntity);

        List<GradeEntity> grades = Arrays.asList(
                GradeStubs.createGradeEntity(),
                GradeStubs.createGradeEntity2(),
                GradeStubs.createGradeEntity3());

        when(gradeRepository.findByStudentId(studentEntity.getId())).thenReturn(Optional.of(grades));

        // Act
        GradeListResponse response = gradeService.getGradesByStudent(searchValue);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Grades retrieved successfully", response.getMessage());
        assertEquals(grades.size(), response.getGrades().size());
        List<GradeResponse> expectedGradeResponses = grades.stream()
                .map(GradeResponse::convertToResponseModel)
                .collect(Collectors.toList());
        assertEquals(expectedGradeResponses, response.getGrades());
    }

}