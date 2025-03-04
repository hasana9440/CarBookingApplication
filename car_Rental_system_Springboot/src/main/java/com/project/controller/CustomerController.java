package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.BookCarDto;
import com.project.dto.carDTO;
import com.project.service.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class CustomerController {
	@Autowired
	private CustomerService service;
	
	@GetMapping("/cars")
	public ResponseEntity<List<carDTO>> getAllCars(){
		List<carDTO> list = service.getAllCars();
		return new ResponseEntity<List<carDTO>>(list,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/car/book")
	public  ResponseEntity<Void> bookCar(@RequestBody BookCarDto dto){
		boolean success = service.bookACar(dto);
		if(success) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@GetMapping("/car/{id}")
	public ResponseEntity<carDTO> getCarById(@PathVariable Long id){
		carDTO dto = service.getcarById(id);
		if(dto == null)return ResponseEntity.notFound().build();
		else {
			return ResponseEntity.ok(dto);
		}
	}
	@GetMapping("/car/booking/{id}")
	public ResponseEntity<List<BookCarDto>> getBookingByUserId(@PathVariable Long id){
		List<BookCarDto> dto = service.getBookingByUserId(id);
		System.out.println("reached controller");
		System.out.println(dto);
		return ResponseEntity.ok(dto);
	}
}
