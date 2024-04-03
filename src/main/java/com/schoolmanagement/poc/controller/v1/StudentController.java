package com.schoolmanagement.poc.controller.v1;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.StudentRequest;
import com.schoolmanagement.poc.model.response.StudentListResponse;
import com.schoolmanagement.poc.model.response.StudentResponse;
import com.schoolmanagement.poc.repository.StudentRepository;
import com.schoolmanagement.poc.service.StudentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @ApiOperation(value = "Get all students", response = StudentListResponse.class)
    public ResponseEntity<StudentListResponse> getAllStudent(
            @RequestHeader(value = "Authorization") String token
    ) {
        log.info("Getting all students");
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a student by id", response = StudentResponse.class)
    public ResponseEntity<StudentResponse> getStudentById(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String id) {
        log.info("Getting a student by id");
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a student", response = StudentResponse.class)
    public ResponseEntity<StudentResponse> createStudent(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid StudentRequest studentRequest) {
        log.info("Creating a student");
        return ResponseEntity.ok(studentService.create(studentRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a student", response = StudentResponse.class)
    public ResponseEntity<StudentResponse> updateStudent(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid StudentRequest studentRequest,
            @PathVariable String id) {
        log.info("Updating a student");
        return ResponseEntity.ok(studentService.update(studentRequest, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a student", response = ResponseModel.class)
    public ResponseEntity<ResponseModel> deleteStudent(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String id) {
        log.info("Deleting a student");
        return ResponseEntity.ok(studentService.delete(id));
    }

}