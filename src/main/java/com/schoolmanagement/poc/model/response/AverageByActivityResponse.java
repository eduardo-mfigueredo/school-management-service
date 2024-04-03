package com.schoolmanagement.poc.model.response;

import com.schoolmanagement.poc.model.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class AverageByActivityResponse extends ResponseModel {

    private String activityId;
    private Double average;

}