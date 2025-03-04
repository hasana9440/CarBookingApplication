package com.project.service.auth;

import com.project.dto.SignupRequest;
import com.project.dto.UserDTO;

public interface AuthService {
	UserDTO createUser(SignupRequest signuprequest);
	Boolean emailAlreadyExist(String email);
}
