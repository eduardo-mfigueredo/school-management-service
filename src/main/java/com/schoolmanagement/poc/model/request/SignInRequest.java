package com.schoolmanagement.poc.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignInRequest {

    @NotNull
    @Email
    private String username;
    @NotNull
    private String password;

}
