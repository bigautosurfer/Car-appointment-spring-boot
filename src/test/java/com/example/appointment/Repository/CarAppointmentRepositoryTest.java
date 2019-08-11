package com.example.appointment.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.appointment.model.CarAppointment;
import com.example.appointment.repository.CarAppointmentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarAppointmentRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CarAppointmentRepository carAppointmentRepository;
	
	@Test
	public void whenDeleted_AppointmentNoLongerExists() {
		CarAppointment one = new CarAppointment("1", LocalDateTime.now(), LocalDateTime.now(), "booked", 3);
		entityManager.persistAndFlush(one);
		Optional<CarAppointment> car = carAppointmentRepository.findById("1");
		assertThat(car.get().getLicensePlate()).isEqualTo("1");
		carAppointmentRepository.delete(one);
		car = carAppointmentRepository.findById("1");
		assertThat(car.isPresent()).isEqualTo(false);
	}
	
	@Test
	public void whenCreate_AppointmentIsCreated() {
		CarAppointment one = new CarAppointment("4", LocalDateTime.now(), LocalDateTime.now(), "booked", 4);
		carAppointmentRepository.save(one);
		Optional<CarAppointment> car = carAppointmentRepository.findById("4");
		assertThat(car.get().getLicensePlate()).isEqualTo("4");
	}
	
	@Test
	public void whenValidTimeRange_AppointmentsAreSorted() {
		CarAppointment one = new CarAppointment("7", LocalDateTime.now(), LocalDateTime.now(), "booked", 3);
		CarAppointment two = new CarAppointment("2", LocalDateTime.now(), LocalDateTime.now(), "booked", 6);
		CarAppointment three = new CarAppointment("3", LocalDateTime.now(), LocalDateTime.now(), "booked", 1);
		entityManager.persist(one);
		entityManager.persist(two);
		entityManager.persist(three);
		entityManager.flush();
		List<CarAppointment> list = carAppointmentRepository.findAllByAppoinmentStartTimeBetweenOrderByPriceDesc(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
		assertThat(list.get(0).getPrice()).isEqualTo(6);
	}
}
