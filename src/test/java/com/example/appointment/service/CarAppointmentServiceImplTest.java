package com.example.appointment.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.appointment.model.CarAppointment;
import com.example.appointment.repository.CarAppointmentRepository;
import com.example.appointment.service.CarAppointmentService;
import com.example.appointment.service.CarAppointmentServiceImpl;

@RunWith(SpringRunner.class)
public class CarAppointmentServiceImplTest {

	@TestConfiguration
	static class CarAppointmentServiceImplTestContextConfiguration{
		 @Bean
	        public CarAppointmentService employeeService() {
	            return new CarAppointmentServiceImpl();
	        }
	}
	
	@Autowired
	private CarAppointmentService carAppointmentService;

	@MockBean
	private CarAppointmentRepository carAppointmentRepository;
	
	@Test
	public void whenValidAppointment_thenAppointmentShouldBeSaved() {
		CarAppointment carAppointment = new CarAppointment("1", LocalDateTime.now(), LocalDateTime.now(), "booked", 3);
		Mockito.when(carAppointmentRepository.save(carAppointment)).thenReturn(carAppointment);
		carAppointment = carAppointmentService.create(carAppointment);
		assertThat(carAppointment.getLicensePlate()).isEqualTo("1");
	}
	
	@Test
	public void whenValidLicensePlate_thenAppointmentShouldBeReturned() {
		CarAppointment carAppointment = new CarAppointment("1", LocalDateTime.now(), LocalDateTime.now(), "booked", 3);
		Optional c1 = Optional.of(carAppointment);
		Mockito.when(carAppointmentRepository.findById("1")).thenReturn(c1);
		Optional<CarAppointment> car = carAppointmentService.getAppointment("1");
		assertThat(car.get().getLicensePlate()).isEqualTo("1");
	}
	
	@Test
	public void whenValidTimeRange_thenAppointmentsShouldBeReturned() {
		CarAppointment one = new CarAppointment("1", LocalDateTime.now(), LocalDateTime.now(), "booked", 3);
		CarAppointment two = new CarAppointment("2", LocalDateTime.now(), LocalDateTime.now(), "booked", 2);
		CarAppointment three = new CarAppointment("3", LocalDateTime.now(), LocalDateTime.now(), "booked", 1);
		ArrayList<CarAppointment> list = new ArrayList<CarAppointment>();
		list.add(one);
		list.add(two);
		list.add(three);
		Mockito.when(carAppointmentRepository.findAllByAppoinmentStartTimeBetweenOrderByPriceDesc(LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(list);
		List<CarAppointment> returnList = carAppointmentService.getAllAppointmentByStartDateRangeSortByPrice(LocalDateTime.MIN, LocalDateTime.MAX);
		assertThat(returnList.size()).isEqualTo(3);
	}
}
