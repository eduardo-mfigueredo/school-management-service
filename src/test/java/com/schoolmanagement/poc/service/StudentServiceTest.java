package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.StudentRequest;
import com.schoolmanagement.poc.model.response.StudentListResponse;
import com.schoolmanagement.poc.model.response.StudentResponse;
import com.schoolmanagement.poc.repository.StudentRepository;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
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
import static org.mockito.Mockito.*;

@DisplayName("Tests for StudentService")
@SpringBootTest
@ActiveProfiles("test")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository);
    }

    @Test
    @DisplayName("Test create method")
    void shouldReceiveAStudentRequestAndCreate() {
        // Given
        StudentEntity savedStudentEntity = StudentStubs.createStudentEntity();
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(savedStudentEntity);

        // Act
        StudentResponse studentResponse = studentService.create(StudentStubs.createStudentRequest());

        // Assert
        assertEquals(StudentStubs.createStudentResponse(), studentResponse);
        assertNotNull(studentResponse);
        assertEquals(savedStudentEntity.getId(), studentResponse.getId());
        assertEquals(savedStudentEntity.getName(), studentResponse.getName());
        verify(studentRepository, times(1)).save(any(StudentEntity.class));
    }

    @Test
    @DisplayName("Test findAll method")
    void shouldReturnAllStudents() {
        // Given
        List<StudentEntity> studentEntities = Arrays.asList(
                StudentStubs.createStudentEntity(),
                StudentStubs.createStudentEntity2(),
                StudentStubs.createStudentEntity3());
        when(studentRepository.findAll()).thenReturn(studentEntities);

        // Act
        StudentListResponse studentListResponse = studentService.findAll();

        // Assert
        assertNotNull(studentListResponse);
        assertTrue(studentListResponse.isSuccess());
        assertEquals("Students retrieved successfully", studentListResponse.getMessage());
        List<StudentResponse> expectedStudentResponses = studentEntities.stream()
                .map(StudentResponse::convertToResponseModel)
                .collect(Collectors.toList());
        assertEquals(expectedStudentResponses, studentListResponse.getStudents());
    }

    @Test
    @DisplayName("Test findById method - Student found")
    void shouldFindAStudentByIdWithSuccess() {
        // Given
        StudentEntity studentEntity = StudentStubs.createStudentEntity();
        when(studentRepository.findById(anyString())).thenReturn(Optional.of(studentEntity));

        // Act
        StudentResponse studentResponse = studentService.findById("1");

        // Assert
        assertNotNull(studentResponse);
        assertTrue(studentResponse.isSuccess());
        assertEquals(studentEntity.getId(), studentResponse.getId());
    }

    @Test
    @DisplayName("Test findById method - Student not found")
    void shouldFindAStudentByIdAndThrowException() {
        // Given
        when(studentRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        StudentResponse studentResponse = studentService.findById("1");

        // Assert
        assertNotNull(studentResponse);
        assertFalse(studentResponse.isSuccess());
        assertEquals("Student not found", studentResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Student found")
    void shouldUpdateSuccessfullyAStudent() {
        // Given
        StudentEntity existingStudentEntity = StudentStubs.createStudentEntity();
        StudentRequest studentRequest = StudentStubs.createStudentRequest();
        when(studentRepository.findById("1")).thenReturn(Optional.of(existingStudentEntity));
        when(studentRepository.save(any(StudentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StudentResponse studentResponse = studentService.update(studentRequest, "1");

        // Assert
        assertNotNull(studentResponse);
        assertTrue(studentResponse.isSuccess());
        assertEquals("Student updated successfully", studentResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Inactive Student")
    void shouldTryToUpdateAnInactiveStudentAndFalseResponse() {
        // Given
        String id = "1";
        StudentEntity inactiveStudentEntity = StudentStubs.createInactiveStudentEntity();
        StudentRequest studentRequest = StudentStubs.createStudentRequest();
        when(studentRepository.findById(id)).thenReturn(Optional.of(inactiveStudentEntity));

        // Act
        StudentResponse studentResponse = studentService.update(studentRequest, id);

        // Assert
        assertNotNull(studentResponse);
        assertFalse(studentResponse.isSuccess());
        assertEquals("Student is inactive", studentResponse.getMessage());
    }

    @Test
    @DisplayName("Test update method - Student not found")
    void shouldTryToUpdateAStudentAndThrowANotFoundException() {
        // Given
        String id = "1";
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        StudentResponse studentResponse = studentService.update(StudentStubs.createStudentRequest(), id);

        // Assert
        assertNotNull(studentResponse);
        assertFalse(studentResponse.isSuccess());
        assertEquals("Student not found", studentResponse.getMessage());
    }

    @Test
    @DisplayName("Test delete method - Student found")
    void shouldDeleteAStudentSuccessfully() {
        // Given
        String id = "1";
        StudentEntity existingStudentEntity = StudentStubs.createStudentEntity();
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudentEntity));
        when(studentRepository.save(any(StudentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseModel responseModel = studentService.delete(id);

        // Assert
        assertNotNull(responseModel);
        assertTrue(responseModel.isSuccess());
        assertEquals("Student deleted successfully", responseModel.getMessage());
    }

    @Test
    @DisplayName("Test delete method - Student not found")
    void shouldTryToDeleteAStudentAndThrowException() {
        // Given
        String id = "1";
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> studentService.delete(id), "Student not found");
    }

    @Test
    @DisplayName("Test findBySearchValue method - Student found")
    void shouldFindBySearchValueSuccessfully() {
        // Given
        StudentEntity existingStudentEntity = StudentStubs.createStudentEntity();
        when(studentRepository.findStudentBySearchValue(any(String.class))).thenReturn(Optional.of(existingStudentEntity));

        // Act
        StudentEntity studentEntity = studentService.findBySearchValue("Joao da Silva");

        // Assert
        assertNotNull(studentEntity);
        assertEquals(existingStudentEntity, studentEntity);
    }

    @Test
    @DisplayName("Test findBySearchValue method - Student not found")
    void shouldTryToFindBySearchValueAndThrowAnException() {
        // Given
        when(studentRepository.findStudentBySearchValue(any(String.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class,
                () -> studentService.findBySearchValue("Non-existent student"), HttpStatus.NOT_FOUND.toString());
    }

}