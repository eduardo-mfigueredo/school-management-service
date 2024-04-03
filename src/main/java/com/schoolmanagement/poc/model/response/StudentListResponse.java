package com.schoolmanagement.poc.model.response;

import com.schoolmanagement.poc.model.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StudentListResponse extends ResponseModel {

    private List<StudentResponse> students;

}