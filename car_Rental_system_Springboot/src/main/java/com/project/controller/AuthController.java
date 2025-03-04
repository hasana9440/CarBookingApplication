package com.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.AuthenticationRequest;
import com.project.dto.AuthenticationResponse;
import com.project.dto.SignupRequest;
import com.project.dto.UserDTO;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.auth.AuthService;
import com.project.service.jwt.UserService;
import com.project.utils.JWTUtils;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	@Autowired
	private AuthService service;
	@Autowired
	private UserRepository repo;
	@Autowired
	private UserService uService;
	@Autowired
	private JWTUtils jwt;
	@Autowired
	private final AuthenticationManager manager;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupcustomer(@RequestBody SignupRequest signuprequest){
		
		
		if(service.emailAlreadyExist(signuprequest.getEmail())) {
			return new ResponseEntity<>("Customer With this email already Existed try another !! ",HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			UserDTO dto = service.createUser(signuprequest);
			if(dto== null) {
				return new ResponseEntity<>("Customer Not Created come again later !! ",HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<>(dto,HttpStatus.OK);
			}
		}
		
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authrequest )
	throws BadCredentialsException,DisabledException,
	UsernameNotFoundException{
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getEmail(),authrequest.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Username and password");
		}
		final UserDetails userdetails = uService.userdetailsservice().loadUserByUsername(authrequest.getEmail());
		Optional<User> user = repo.findByEmail(userdetails.getUsername());
		final String JWT = jwt.generateToken(userdetails);
		AuthenticationResponse response = new AuthenticationResponse();
		if(user.isPresent()) {
			response.setJwt(JWT);
			response.setId(user.get().getId());
			response.setUserrole(user.get().getUserRole());
			
		}
		return response;
	}
}
