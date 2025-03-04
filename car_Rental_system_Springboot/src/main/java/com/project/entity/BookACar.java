////package com.project.entity;
////
////import java.util.Date;
////
////import org.hibernate.annotations.OnDelete;
////import org.hibernate.annotations.OnDeleteAction;
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import com.project.enums.BookCarStatus;
////
////import jakarta.persistence.Entity;
////import jakarta.persistence.FetchType;
////import jakarta.persistence.GeneratedValue;
////import jakarta.persistence.GenerationType;
////import jakarta.persistence.Id;
////import jakarta.persistence.JoinColumn;
////import jakarta.persistence.ManyToOne;
////import jakarta.persistence.Table;
////import lombok.Data;
////
////@Entity
////@Table(name= "BookACar")
////@Data
////public class BookACar {
////	@Id
////	@GeneratedValue(strategy = GenerationType.IDENTITY)
////	private Long id;
////	
////	private Date fromDate;
////	
////	private Date toDate;
////	
////	private Long days;
////	
////	private Long Price;
////	
////	private BookCarStatus bookCarStatus;
////	
////	@ManyToOne(fetch = FetchType.LAZY,optional=false)
////	@JoinColumn(name = "user_id",nullable = false)
////	@OnDelete(action = OnDeleteAction.CASCADE)
////	@JsonIgnore
////	private User user;
////	
////	private Car car;
////}
//
//
//package com.project.entity;
//
//import java.util.Date;
//
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.project.dto.BookCarDto;
//import com.project.enums.BookCarStatus;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//import lombok.Data;
//
//@Entity
//@Table(name = "BookACar")
//@Data
//public class BookACar {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @Temporal(TemporalType.DATE)
//    private Date fromDate;
//    
//    @Temporal(TemporalType.DATE)
//    private Date toDate;
//    
//    private Long days;
//    
//    private Long price;  // ✅ Changed variable name from "Price" to "price"
//    
//    @Enumerated(EnumType.STRING)  // ✅ Added @Enumerated for proper enum mapping
//    private BookCarStatus bookCarStatus;
//    
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private User user;
//    
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // ✅ Added correct mapping for Car entity
//    @JoinColumn(name = "car_id", nullable = false)
//    private Car car;
//    
//    public BookCarDto getBookACarDto() {
//    	BookCarDto dto = new BookCarDto();
//    	dto.setId(id);
//    	dto.setDays(days);
//    	dto.setBookCarStatus(bookCarStatus);
//    	dto.setPrice(price);
//    	dto.setToDate(toDate);
//    	dto.setFromDate(fromDate);
//    	dto.setEmail(user.getEmail());
//    	dto.setUserId(user.getId());
//    	dto.setUserName(user.getUsername());
//    	dto.setCarId(car.getId());
//    	return dto;
//    }
//}
//

package com.project.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dto.BookCarDto;
import com.project.enums.BookCarStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "BookACar")
@Data
public class BookACar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;

    private Long days;

    private Long price;  // ✅ Changed variable name from "Price" to "price"

    @Enumerated(EnumType.STRING)  // ✅ Added @Enumerated for proper enum mapping
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // ✅ Added correct mapping for Car entity
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public BookCarDto getBookACarDto() {
        BookCarDto dto = new BookCarDto();
        dto.setId(id);
        dto.setDays(days);
        dto.setBookCarStatus(bookCarStatus);
        dto.setPrice(price);
        dto.setToDate(toDate);
        dto.setFromDate(fromDate);
        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUsername());
        dto.setUserId(user.getId());
        dto.setCarId(car.getId());
//        // Handle null values for user and car
//        if (user != null) {
//            dto.setEmail(user.getEmail());
//            dto.setUserId(user.getId());
//            dto.setUserName(user.getUsername());
//        }
//
//        if (car != null) {
//            dto.setCarId(car.getId());
//        }

        return dto;
    }
}
