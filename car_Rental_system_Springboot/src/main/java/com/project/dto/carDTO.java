package com.project.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class carDTO {
	private Long id;
	private String brand;
	private String type;
	private String name;
	private String color;
	private String description;
	private String transmission;
	private Long price;
	private byte[] returenedFile;
	private MultipartFile img;
}
