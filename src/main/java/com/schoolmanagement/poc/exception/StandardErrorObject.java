package com.schoolmanagement.poc.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StandardErrorObject {

    private Integer status;
    private String errorMessage;
    private String message;
    private String path;

}