package com.schoolmanagement.poc.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {

    UserDetailsService userDetailsService();

}