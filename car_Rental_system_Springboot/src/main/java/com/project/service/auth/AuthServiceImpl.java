package com.project.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dto.SignupRequest;
import com.project.dto.UserDTO;
import com.project.entity.User;
import com.project.enums.UserRole;
import com.project.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	@Autowired
	private final UserRepository repo;
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount= repo.findByUserRole(UserRole.ADMIN);
		if(adminAccount == null) {
			User newAdmin = new User();
			newAdmin.setName("admin");
			newAdmin.setEmail("admin@gmail.com");
			newAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdmin.setUserRole(UserRole.ADMIN);
			repo.save(newAdmin);
			System.out.println("Admin Account is created successfully !");
		}
	}
	@Override
	public UserDTO createUser(SignupRequest signuprequest) {
		User user = new User();
		user.setEmail(signuprequest.getEmail());
		user.setName(signuprequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signuprequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		
		User createdUser = repo.save(user);
		UserDTO dto = new UserDTO();
		dto.setEmail(createdUser.getEmail());
		dto.setId(createdUser.getId());
		return dto;
	}

	@Override
	public Boolean emailAlreadyExist(String email) {
		return repo.findByEmail(email).isPresent();
//		User user =  repo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Not found"));
//		System.out.println(user.getEmail() == email);
//		if(user.getEmail() == email) {
//			System.out.println(user.getEmail());
//			System.out.println(email);
//			
//			return true;
//			
//		}
//		else {
//			System.out.println(user.getEmail());
//			System.out.println(email);
//		
//			return false;
//		}
	}
	
}
