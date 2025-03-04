package com.project.service.admin;

import java.util.List;

import com.project.dto.BookCarDto;
import com.project.dto.CarDtoListDto;
import com.project.dto.SearchCarDto;
import com.project.dto.carDTO;

public interface AdminService {
	boolean postCar(carDTO dto) throws Exception;
	List<carDTO> getAllCars();
	void deleteCar(long id);
	carDTO getcarById(Long id);
	boolean updateCar(Long id,carDTO Dto) throws Exception;
	List<BookCarDto> getBookings();
	boolean chnageBookingStatus(Long id,String status);
	CarDtoListDto serachCar(SearchCarDto dto);
}
