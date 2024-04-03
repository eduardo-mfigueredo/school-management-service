package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.StudentRequest;
import com.schoolmanagement.poc.model.response.StudentListResponse;
import com.schoolmanagement.poc.model.response.StudentResponse;
import com.schoolmanagement.poc.repository.StudentRepository;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
import com.schoolmanagement.poc.service.interfaces.ICrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService implements ICrudService<StudentRequest, StudentResponse, StudentListResponse> {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        return StudentResponse.convertToResponseModel(
                studentRepository.save(StudentRequest.convertToEntity(studentRequest)));
    }

    @Override
    public StudentListResponse findAll() {
        return StudentListResponse.builder()
                .success(true)
                .message("Students retrieved successfully")
                .students(studentRepository.findAll()
                        .stream()
                        .map(StudentResponse::convertToResponseModel)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public StudentResponse findById(String id) {
        return studentRepository.findById(id)
                .map(StudentResponse::convertToResponseModel)
                .orElse(new StudentResponse(false, "Student not found"));
    }

    @Override
    public StudentResponse update(StudentRequest studentRequest, String id) {
        return studentRepository.findById(id)
                .map(studentEntity -> {

                    if (!studentEntity.isActive()) {
                        return new StudentResponse(false, "Student is inactive");
                    }

                    studentEntity.setName(studentRequest.getName());
                    studentEntity.setEmail(studentRequest.getEmail());
                    studentEntity.setPhone(studentRequest.getPhone());
                    studentEntity.setDocumentNumber(studentRequest.getDocumentNumber());
                    studentEntity.setUpdatedAt(ZonedDateTime.now().toString());

                    StudentResponse studentResponse =
                            StudentResponse.convertToResponseModel(
                                    studentRepository.save(studentEntity));

                    studentResponse.setMessage("Student updated successfully");
                    return studentResponse;
                })
                .orElse(new StudentResponse(false, "Student not found"));
    }

    @Override
    public ResponseModel delete(String id) {
        return studentRepository.findById(id).map(studentEntity -> {
            studentEntity.setActive(false);
            studentRepository.save(studentEntity);
            return ResponseModel.builder().success(true).message("Student deleted successfully").build();
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public StudentEntity findBySearchValue(String searchValue) {
       return studentRepository.findStudentBySearchValue(searchValue)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found"));
    }

}