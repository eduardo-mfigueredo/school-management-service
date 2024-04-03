package com.schoolmanagement.poc.controller.v1;

import com.schoolmanagement.poc.model.request.GradeRequest;
import com.schoolmanagement.poc.model.response.*;
import com.schoolmanagement.poc.service.GradeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
@Slf4j
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    @ApiOperation(value = "Get all grades", response = GradeListResponse.class)
    public ResponseEntity<GradeListResponse> getAllGrades(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Getting all grades");
        return ResponseEntity.ok(gradeService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a grade by id", response = GradeResponse.class)
    public ResponseEntity<GradeResponse> getGradeById(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String id) {
        log.info("Getting a grade by id");
        return ResponseEntity.ok(gradeService.findById(id));
    }

    @GetMapping("/student/{studentId}")
    @ApiOperation(value = "Get all grades by student id", response = GradeListResponse.class)
    public ResponseEntity<GradeListResponse> getGradesByStudentId(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String studentId) {
        log.info("Getting all grades by student id");
        return ResponseEntity.ok(gradeService.findByStudentId(studentId));
    }

    @GetMapping("/student/average/activity")
    @ApiOperation(value = "Endpoint to query the overall average of a student in a specific activity",
            response = AverageByStudentAndActivityResponse.class)
    public ResponseEntity<AverageByStudentAndActivityResponse> getAverageByStudentAndActivity(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String studentId,
            @RequestParam String activityId) {
        log.info("Getting average grade for student {} on activity {}", studentId, activityId);
        return ResponseEntity.ok(gradeService.getStudentAverage(studentId, activityId));
    }

    @GetMapping("/student/average/")
    @ApiOperation(value = "Endpoint to query the overall average of a student based on all their responses, " +
            "regardless of the activity.",
            response = AverageByStudentResponse.class)
    public ResponseEntity<AverageByStudentResponse> getAverageByStudent(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String studentId) {
        log.info("Getting average grade for student {}", studentId);
        return ResponseEntity.ok(gradeService.getStudentAverage(studentId));
    }

    @GetMapping("/student/")
    @ApiOperation(value = "Endpoint to list all grades of a specific student, searchable by CPF, email, or phone number.",
            response = GradeListResponse.class)
    public ResponseEntity<GradeListResponse> getGradesByStudent(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String searchValue) {
        log.info("Getting grade for student  {}", searchValue);
        return ResponseEntity.ok(gradeService.getGradesByStudent(searchValue));
    }

    @GetMapping("/activity/average/")
    @ApiOperation(value = "Endpoint to query the average of a specific activity based on all students.",
            response = AverageByActivityResponse.class)
    public ResponseEntity<AverageByActivityResponse> getAverageByActivity(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String activityId) {
        log.info("Getting average grade for activity {}", activityId);
        return ResponseEntity.ok(gradeService.getActivityAverage(activityId));
    }

    @PostMapping
    @ApiOperation(value = "Create a grade", response = GradeResponse.class)
    public ResponseEntity<GradeResponse> createGrade(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid GradeRequest gradeRequest) {
        log.info("Creating a grade");
        return ResponseEntity.ok(gradeService.create(gradeRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a grade", response = GradeResponse.class)
    public ResponseEntity<GradeResponse> updateGrade(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody GradeRequest gradeRequest,
            @PathVariable @Valid String id) {
        log.info("Updating a grade");
        return ResponseEntity.ok(gradeService.update(gradeRequest, id));
    }

}