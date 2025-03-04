package com.project.service.customer;

import java.util.List;

import com.project.dto.BookCarDto;
import com.project.dto.carDTO;

public interface CustomerService {
	
	List<carDTO> getAllCars();
	
	boolean bookACar(BookCarDto dto);
	
	carDTO getcarById(Long id);
	
	List<BookCarDto> getBookingByUserId(long userId);
}
