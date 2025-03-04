package com.project.service.admin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.project.dto.BookCarDto;
import com.project.dto.CarDtoListDto;
import com.project.dto.SearchCarDto;
import com.project.dto.carDTO;
import com.project.entity.BookACar;
import com.project.entity.Car;
import com.project.enums.BookCarStatus;
import com.project.repository.BookCarRepository;
import com.project.repository.CarRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private CarRepository repo;
	@Autowired
	private BookCarRepository BRepo;
	@Override
	public boolean postCar(carDTO dto) throws Exception {
		try{
			
			Car car = new Car();
			car.setBrand(dto.getBrand());
			car.setColor(dto.getColor());
			car.setDescription(dto.getDescription());
			car.setName(dto.getName());
			car.setPrice(dto.getPrice());
			car.setTransmission(dto.getTransmission());
			car.setType(dto.getType());
			car.setImage(dto.getImg().getBytes());
			repo.save(car);
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}

	@Override
	public List<carDTO> getAllCars() {
		
		return repo.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCar(long id) {
		repo.deleteById(id);
		
	}

	@Override
	public carDTO getcarById(Long id) {
		Optional<Car> car = repo.findById(id);
		
		return car.map(Car::getCarDto).orElse(null);
	}

	@Override
	public boolean updateCar(Long id, carDTO Dto) throws Exception {
	    try {
	        Optional<Car> carex = repo.findById(id);

	        if (carex.isPresent()) {
	            Car car = carex.get(); // ✅ Use the existing car object instead of creating a new one

	            car.setBrand(Dto.getBrand());
	            car.setColor(Dto.getColor());
	            car.setDescription(Dto.getDescription());
	            car.setName(Dto.getName());
	            car.setPrice(Dto.getPrice());
	            car.setTransmission(Dto.getTransmission());
	            car.setType(Dto.getType());

	            if (Dto.getImg() != null) { // ✅ Only update image if it's provided
	                car.setImage(Dto.getImg().getBytes());
	            }

	            repo.save(car); // ✅ Save the updated object

	            System.out.println("Car updated successfully: " + car);
	            return true;
	        } else {
	            System.out.println("Car not found with ID: " + id);
	            return false;
	        }
	    } catch (Exception e) {
	        System.out.println("Exception occurred while updating car: " + e.getMessage());
	        return false;
	    }
	}

	@Override
	public List<BookCarDto> getBookings() {
		return  BRepo.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
		
	}

	@Override
	public boolean chnageBookingStatus(Long id, String status) {
		Optional<BookACar> dto = BRepo.findById(id);
		if(dto.isPresent()) {
			BookACar car = dto.get();
			if(Objects.equals(status, "APPROVE")) {
				car.setBookCarStatus(BookCarStatus.APPROVED);;
			}
			else {
				car.setBookCarStatus(BookCarStatus.REJECTED);
			}
			BRepo.save(car);
			return true;
		}
		System.out.println(dto);
		return false;
	}

	@Override
	public CarDtoListDto serachCar(SearchCarDto dto) {
		Car car = new Car();
		car.setBrand(dto.getBrand());
		car.setColor(dto.getColor());
		car.setTransmission(dto.getTransmission());
		car.setType(dto.getType());
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
		.withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
		.withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
		.withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> example = Example.of(car,matcher);
		List<Car> list = repo.findAll(example);
		CarDtoListDto Dtolist = new CarDtoListDto();
		Dtolist.setCarDtoList(list.stream().map(Car::getCarDto).collect(Collectors.toList()));
		return Dtolist;
		//return null;
	}

}
