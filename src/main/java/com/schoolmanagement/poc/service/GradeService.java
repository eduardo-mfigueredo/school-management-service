package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.ResponseModel;
import com.schoolmanagement.poc.model.request.GradeRequest;
import com.schoolmanagement.poc.model.response.*;
import com.schoolmanagement.poc.repository.GradeRepository;
import com.schoolmanagement.poc.repository.entities.GradeEntity;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
import com.schoolmanagement.poc.service.interfaces.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService implements ICrudService<GradeRequest, GradeResponse, GradeListResponse> {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;

    @Override
    public GradeResponse create(GradeRequest requestModel) {
        return GradeResponse.convertToResponseModel(
                gradeRepository.save(GradeRequest.convertToEntity(requestModel)));
    }

    @Override
    public GradeListResponse findAll() {
        return GradeListResponse.builder()
                .success(true)
                .message("Grades retrieved successfully")
                .grades(gradeRepository.findAll()
                        .stream()
                        .map(GradeResponse::convertToResponseModel)
                        .collect(Collectors.toList())).build();
    }

    @Override
    public GradeResponse findById(String id) {
        return gradeRepository.findById(id)
                .map(GradeResponse::convertToResponseModel)
                .orElse(GradeResponse.builder().success(false).message("Grade not found").build());
    }

    public GradeListResponse findByStudentId(String studentId) {
        return GradeListResponse.builder()
                .success(true)
                .message("Grades retrieved successfully")
                .grades(gradeRepository.findByStudentId(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found"))
                        .stream()
                        .map(GradeResponse::convertToResponseModel)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GradeResponse update(GradeRequest gradeRequest, String id) {
        return gradeRepository.findById(id)
                .map(gradeEntity -> {
                    gradeEntity.setGrade(gradeRequest.getGrade());
                    gradeEntity.setUpdatedAt(ZonedDateTime.now().toString());

                    GradeResponse gradeResponse =
                            GradeResponse.convertToResponseModel(
                                    gradeRepository.save(gradeEntity));

                    gradeResponse.setMessage("Grade updated successfully");
                    return gradeResponse;
                })
                .orElse(GradeResponse.builder().success(false).message("Grade not found").build());
    }


    //    Método delete não foi implementado intencionalmente.
    @Override
    public ResponseModel delete(String id) {
        return null;
    }

    public AverageByStudentAndActivityResponse getStudentAverage(String studentId, String activityId) {
        return gradeRepository.findByStudentIdAndActivityId(studentId, activityId)
                .map(grades -> {
                    OptionalDouble average = grades.stream()
                            .mapToDouble(GradeEntity::getGrade)
                            .average();
                    return AverageByStudentAndActivityResponse.builder()
                            .success(true)
                            .message("Average grade retrieved successfully")
                            .studentId(studentId)
                            .activityId(activityId)
                            .average(average.orElse(0.0))
                            .build();
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found for studentId: "
                                + studentId + " and activityId: " + activityId));
    }

    public AverageByStudentResponse getStudentAverage(String studentId) {
        return gradeRepository.findByStudentId(studentId)
                .map(grades -> {
                    OptionalDouble average = grades.stream()
                            .mapToDouble(GradeEntity::getGrade)
                            .average();
                    return AverageByStudentResponse.builder()
                            .success(true)
                            .message("Average grade retrieved successfully")
                            .studentId(studentId)
                            .average(average.orElse(0.0))
                            .build();
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found for studentId: "
                                + studentId));
    }

    public AverageByActivityResponse getActivityAverage(String activityId) {
        return gradeRepository.findByActivityId(activityId)
                .map(grades -> {
                    OptionalDouble average = grades.stream()
                            .mapToDouble(GradeEntity::getGrade)
                            .average();
                    return AverageByActivityResponse.builder()
                            .success(true)
                            .message("Average grade retrieved successfully")
                            .activityId(activityId)
                            .average(average.orElse(0.0))
                            .build();
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found for activityId: " + activityId));
    }

    public GradeListResponse getGradesByStudent(String searchValue) {
        StudentEntity studentEntity = studentService.findBySearchValue(searchValue);

        return GradeListResponse.builder()
                .success(true)
                .message("Grades retrieved successfully")
                .grades(gradeRepository.findByStudentId(studentEntity.getId())
                        .orElseThrow(() -> new RuntimeException("Student not found"))
                        .stream()
                        .map(GradeResponse::convertToResponseModel)
                        .collect(Collectors.toList()))
                .build();
    }

}