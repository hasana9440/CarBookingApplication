//package com.project.dto;
//
//import java.util.Date;
//
//import com.project.enums.BookCarStatus;
//
//import lombok.Data;
//@Data
//public class BookCarDto {
//	
//	private Long id;
//	
//	private Date fromDate;
//	
//	private Date toDate;
//	
//	private Long days;
//	
//	private Long price;
//	
//	private BookCarStatus bookCarStatus;
//	
//	private Long carId;
//	
//	private Long userId;
//	
//	private String userName;
//	
//	private String email;
//}
package com.project.dto;

import java.util.Date;

import com.project.enums.BookCarStatus;

import lombok.Data;

@Data  // ✅ Added @Data annotation
public class BookCarDto {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long days;

    private Long price;  // ✅ Changed variable name from "Price" to "price"

    private BookCarStatus bookCarStatus;

    private Long carId;

    private Long userId;

    private String userName;

    private String email;
}