package com.example.appointment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.appointment.Exception.CarAppointmentNotFoundException;
import com.example.appointment.Exception.ErrorResponse;
import com.example.appointment.Exception.InvalidDateException;
import com.example.appointment.model.CarAppointment;
import com.example.appointment.service.CarAppointmentService;

/**
 * controller to handle api request for car appointment
 * @author xiao
 *
 */

@RestController
@RequestMapping("/api/carappointments")
public class CarAppointmentController {

	@Autowired
	private CarAppointmentService carAppointmentService;
	
	private  final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
	
	public CarAppointmentController(CarAppointmentService carAppointmentService) {
		
	}
	
	@GetMapping(path = "/getappointment/{licensePlate}", produces = "application/json")
	public Optional<CarAppointment> getCarAppointment(@PathVariable String licensePlate) {
		Optional<CarAppointment> carAppointment =  carAppointmentService.getAppointment(licensePlate);
		if(carAppointment.isPresent()) {
			return carAppointment;
		}else {
			throw new CarAppointmentNotFoundException("Car appointment not found for License Plate " + licensePlate);
		}
	}
	/**
	 * Path to get all appointment
	 * @param startTime
	 * @param endTime
	 * @return all appointments within the time range sorted by price
	 */
	@GetMapping(path = "/sortappointmentsbytimeprice", produces = "application/json")
	 public List<CarAppointment> findByDateRangeSortedByPrice(@RequestParam(value = "startTime", required = true) String startTime, @RequestParam(value = "endTime", required = true) String endTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
		try {
		LocalDateTime start = LocalDateTime.parse(startTime, formatter);
		LocalDateTime end = LocalDateTime.parse(endTime, formatter);
		return carAppointmentService.getAllAppointmentByStartDateRangeSortByPrice(start, end);
		} catch (DateTimeParseException ex) {
			throw new InvalidDateException("Invalid Start or End Time Found");
		}
	}
	
	/**
	 * path to delete an appointment
	 * @param licensePlate
	 */
	@DeleteMapping(path = "/deleteappointment/{licensePlate}")
	public void delete(@PathVariable String licensePlate) {
		carAppointmentService.delete(licensePlate);
	}
	
	/**
	 *  Path to create an car appointment given the json information for the appointment
	 * @param carAppointment
	 * @return  car appointment created
	 */
	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public CarAppointment create(@RequestBody CarAppointment carAppointment) {
		return carAppointmentService.create(carAppointment);
	}
	
	/**
	 * Path to randomly create an car appointment given the license plate
	 * @param licensePlate
	 * @return Appointment created
	 */
	@PostMapping(path = "/create/{licensePlate}", produces = "application/json")
	public CarAppointment createRandom(@PathVariable(value = "licensePlate", required = true) String licensePlate) {
		return carAppointmentService.create(new CarAppointment(licensePlate));
	}
	
	/**
	 * Path to update the status of an appointment
	 * @param licensePlate
	 * @param status
	 * @return Appointment that was updated
	 */
	@PutMapping(path = "/update/{licensePlate}", produces = "application/json")
	public Optional<CarAppointment> update(@PathVariable(value = "licensePlate", required = true) String licensePlate, @RequestParam(value = "status", required = true) String status) {
		Optional<CarAppointment> carAppointment =  carAppointmentService.getAppointment(licensePlate);
		if(carAppointment.isPresent()) {
			CarAppointment app = carAppointment.get();
			app.setStatus(status);
			carAppointmentService.create(app);
			return carAppointment;
		}else {
			throw new CarAppointmentNotFoundException("Car appointment not found for License Plate " + licensePlate);
		}
	}
}
