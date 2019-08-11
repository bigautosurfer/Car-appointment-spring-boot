package com.example.appointment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.example.appointment.model.CarAppointment;
import com.example.appointment.repository.CarAppointmentRepository;

/**
 * Car Appointment Service implementation class. Implements all methods needed.
 * @author xiao
 *
 */
@Component("carAppointmentService")
public class CarAppointmentServiceImpl implements CarAppointmentService{

	 @Autowired
	   CarAppointmentRepository carAppointmentRepository;
	
	 /**
	  * Delete appointment from backend database
	  */
	@Override
	public void delete(String licensePlate) {
		carAppointmentRepository.deleteById(licensePlate);
		
	}

	/**
	 * create new appointment in backend database
	 */
	@Override
	public CarAppointment create(CarAppointment appointment) {
		return carAppointmentRepository.save(appointment);
	}

	/**
	 * Find appointment in backend database given license plate
	 */
	@Override
	public Optional<CarAppointment> getAppointment(String licensePlate) {
		return carAppointmentRepository.findById(licensePlate);
	}

	/**
	 * Find all appointment within time range and sort result by price in Descending order.
	 */
	@Override
	public List<CarAppointment> getAllAppointmentByStartDateRangeSortByPrice(LocalDateTime startTime,
			LocalDateTime endTime) {
		return carAppointmentRepository.findAllByAppoinmentStartTimeBetweenOrderByPriceDesc(startTime, endTime);
	}

}
