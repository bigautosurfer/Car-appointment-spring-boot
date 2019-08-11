package com.example.appointment.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to represent a car appointment
 * @author xiao
 *
 */

@Entity
@Table(name = "appointments")
public class CarAppointment {
	private @Id String licensePlate;
	private LocalDateTime appoinmentStartTime;
	private LocalDateTime appointmentEndTime;
	private String status;
	private long price;
	
	public CarAppointment() {
		
	}
	
	public CarAppointment(String licensePlate) {
		this.licensePlate = licensePlate;
		int days = (int) System.currentTimeMillis() % 14;
		this.appoinmentStartTime = LocalDateTime.now().plusDays(days);
		this.appointmentEndTime = this.appoinmentStartTime.plusHours(1);
		this.status = "Booked";
		this.price = System.currentTimeMillis() % 100;
	}
	
	
	public CarAppointment(String licensePlate, LocalDateTime appoinmentStartTime, LocalDateTime appointmentEndTime, String status, long price) {
		this.licensePlate = licensePlate;
		this.appoinmentStartTime = appoinmentStartTime;
		this.appointmentEndTime = appointmentEndTime;
		this.status = status;
	    this.price = price;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public LocalDateTime getAppoinmentStartTime() {
		return appoinmentStartTime;
	}
	public void setAppoinmentStartTime(LocalDateTime appoinmentStartTime) {
		this.appoinmentStartTime = appoinmentStartTime;
	}
	public LocalDateTime getAppointmentEndTime() {
		return appointmentEndTime;
	}
	public void setAppointmentEndTime(LocalDateTime appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}

	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
}
