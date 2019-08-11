package com.example.appointment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.appointment.model.CarAppointment;

/**
 * inferface for car appointment service
 */
public interface CarAppointmentService {

	void delete(String licensePlate);
	
	CarAppointment create(CarAppointment appointment);
	
	Optional<CarAppointment> getAppointment(String licensePlate);
	
	List<CarAppointment> getAllAppointmentByStartDateRangeSortByPrice(LocalDateTime startTime, LocalDateTime endTime);
}
