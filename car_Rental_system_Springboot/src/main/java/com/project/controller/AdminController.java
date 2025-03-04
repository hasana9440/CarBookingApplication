 package com.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.BookCarDto;
import com.project.dto.CarDtoListDto;
import com.project.dto.SearchCarDto;
import com.project.dto.carDTO;
import com.project.service.admin.AdminService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class AdminController {
	@Autowired
	private AdminService service;
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping(value = "/car", consumes = "multipart/form-data")
	public ResponseEntity<?> postCar(@ModelAttribute carDTO dto) throws Exception {
	    logger.info("Received request: {}", dto);

	    if (dto.getImg() == null) {
	        logger.error("Image file is missing in request!");
	        return new ResponseEntity<>("Image file is required", HttpStatus.BAD_REQUEST);
	    }

	    boolean success = service.postCar(dto);
	    if (success) {
	        //return new ResponseEntity<>("Car posted", HttpStatus.ACCEPTED);
	    	return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Car posted"));

	    } else {
	       // return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to post car"));
	    }
	}
	@GetMapping("/cars")
	public ResponseEntity<?> getAllcars(){
		return ResponseEntity.ok(service.getAllCars());
	}
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id){
		service.deleteCar(id);
		return ResponseEntity.ok(null);
	}
	@GetMapping("/car/{id}")
	public ResponseEntity<carDTO> getCarById(@PathVariable Long id){
		carDTO dto = service.getcarById(id);
		return ResponseEntity.ok(dto);
	}
	@PutMapping("/car/{id}")
	public ResponseEntity<Void> updateCar(@PathVariable Long id,@ModelAttribute carDTO dto)throws IOException{
		try {
			
			boolean success = service.updateCar(id,dto);
			System.out.println(success);
			if(success)
			{
				
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			else {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		catch(Exception e) {
			System.out.println("exception raised");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@GetMapping("/car/bookings")
	public ResponseEntity<List<BookCarDto>> getBooking(){
		return ResponseEntity.ok(service.getBookings());
	}
	@GetMapping("/car/bookings/{id}/{status}")
	public ResponseEntity<?> changeBookingStatus(@PathVariable Long id,@PathVariable String status){
		boolean success = service.chnageBookingStatus(id, status);
		if(success) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@PostMapping("/car/search")
	public ResponseEntity<?> serachCar(@RequestBody SearchCarDto dto){
		return ResponseEntity.ok( service.serachCar(dto));
		
	}
}
