package com.example.appointment.repository;

import org.springframework.stereotype.Repository;
import com.example.appointment.model.CarAppointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Connection class to interact with backend database
 * @author xiao
 *
 */
@Repository
public interface CarAppointmentRepository extends JpaRepository<CarAppointment, String>{
	
	 List<CarAppointment> findAllByAppoinmentStartTimeBetweenOrderByPriceDesc(LocalDateTime startTime, LocalDateTime endTime);
}
