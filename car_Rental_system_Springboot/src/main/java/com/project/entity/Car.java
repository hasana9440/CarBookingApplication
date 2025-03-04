package com.project.entity;

import java.util.Date;

import com.project.dto.carDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Table(name= "Cars")
@Entity

public class Car {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String brand;
	private String type;
	private String name;
	private String color;
	private String description;
	private String transmission;
	private Long price;
	@Column(columnDefinition = "longblob")

	private byte[] image;
	
	
	public carDTO getCarDto() {
		carDTO dto = new carDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setBrand(brand);
		dto.setColor(color);
		dto.setType(type);
		dto.setPrice(price);
		dto.setDescription(description);
		dto.setTransmission(transmission);
		dto.setReturenedFile(image);
		return dto;
	}
}

