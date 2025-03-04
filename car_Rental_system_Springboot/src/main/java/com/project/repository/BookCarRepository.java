package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dto.BookCarDto;
import com.project.entity.BookACar;
@Repository
public interface BookCarRepository extends JpaRepository<BookACar,Long> {

	List<BookACar> findAllByUserId(long userId);

	

}
