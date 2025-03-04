package com.project.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.BookCarDto;
import com.project.dto.carDTO;
import com.project.entity.BookACar;
import com.project.entity.Car;
import com.project.entity.User;
import com.project.enums.BookCarStatus;
import com.project.repository.BookCarRepository;
import com.project.repository.CarRepository;
import com.project.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CarRepository Crepo;

	@Autowired
	private UserRepository Urepo;

	@Autowired
	private BookCarRepository Brepo;

	@Override
	public List<carDTO> getAllCars() {
		
		return Crepo.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}



	@Override
	public carDTO getcarById(Long id) {
		Optional<Car> car = Crepo.findById(id);
		
		return car.map(Car::getCarDto).orElse(null);
	}



	@Override
	public boolean bookACar(BookCarDto dto) {
		Optional<Car> car = Crepo.findById(dto.getCarId());
		System.out.println(dto);
		Optional<User> user = Urepo.findById(dto.getUserId());

		if (car.isPresent() && user.isPresent()) {
		    Car car1 = car.get();
		    BookACar book = new BookACar();
		    book.setUser(user.get());
		    book.setCar(car.get());
		    book.setBookCarStatus(dto.getBookCarStatus());
		    book.setFromDate(dto.getFromDate());
		    book.setToDate(dto.getToDate());
		    book.setBookCarStatus(BookCarStatus.PENDING);
		    // Calculate the difference in milliseconds between toDate and fromDate
		    long diffMilliSeconds = dto.getToDate().getTime() - dto.getFromDate().getTime();

		    // Convert milliseconds to days
		    long days = TimeUnit.MILLISECONDS.toDays(diffMilliSeconds); // Fix: Use MILLISECONDS instead of MICROSECONDS
		    book.setDays(days);

		    // Calculate the total price
		    book.setPrice(car1.getPrice() * days);

		    System.out.println(book);
		    Brepo.save(book);
		    return true;
		}
		return false;
	}



	@Override
	public List<BookCarDto> getBookingByUserId(long userId) {
	    System.out.println("Fetching bookings for userId: " + userId);

	    List<BookACar> bookList = Brepo.findAllByUserId(userId);

	    if (bookList.isEmpty()) {
	        System.out.println("No bookings found for userId: " + userId);
	    } else {
	        System.out.println("Found " + bookList.size() + " bookings.");
	    }

	    return bookList.stream()
	                   .map(book -> {
	                       System.out.println("Mapping BookACar with ID: " + book.getId());
	                       return book.getBookACarDto();
	                   })
	                   .collect(Collectors.toList());
	}



}
