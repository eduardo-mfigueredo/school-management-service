package com.schoolmanagement.poc.service.interfaces;

import com.schoolmanagement.poc.model.request.RefreshTokenRequest;
import com.schoolmanagement.poc.model.request.SignInRequest;
import com.schoolmanagement.poc.model.response.SignInResponse;

public interface IAuthenticationService {

    SignInResponse signIn(SignInRequest signInRequest);
    SignInResponse refreshToken(RefreshTokenRequest refreshToken);


}
