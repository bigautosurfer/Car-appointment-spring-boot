package com.example.appointment.controller;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import static org.hamcrest.Matchers.*;

import com.example.appointment.Exception.InvalidDateException;
import com.example.appointment.model.CarAppointment;
import com.example.appointment.service.CarAppointmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(CarAppointmentController.class)
public class CarAppointmentControllerTest {

	 	@Autowired
	    private MockMvc mvc;
	 
	    @MockBean
	    private CarAppointmentService carAppointmentService;
	    
	    @Test
	    public void givenValidLicensePlate_whenGetAppointment_thenReturnJSON() throws Exception {
	    	String lP = "123456";
	    	CarAppointment carAppointment = new CarAppointment(lP);
	    	Optional<CarAppointment> c = Optional.of(carAppointment);
	    	given(carAppointmentService.getAppointment(lP)).willReturn(c);
	    	mvc.perform(get("/api/carappointments/getappointment/{licensePlate}", lP))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("licensePlate", is(lP)));
	    }
	    
	    @Test
	    public void givenInvalidLicensePlate_whenGetAppointment_thenReturnNotFound() throws Exception {
	    	String lP = "123456";
	    	CarAppointment carAppointment = new CarAppointment(lP);
	    	Optional<CarAppointment> c = Optional.of(carAppointment);
	    	given(carAppointmentService.getAppointment(lP)).willReturn(c);
	    	mvc.perform(get("/api/carappointments/getappointment/{licensePlate}", "433"))
	    	.andExpect(status().isNotFound());
	    }
	    
	    @Test
	    public void givenValidTimeRange_whenSortAppointmentsByTimePrice_thenReturnJSONArray() throws Exception {
	    	CarAppointment c1 = new CarAppointment("1");
	    	CarAppointment c2 = new CarAppointment("2");
	    	ArrayList<CarAppointment> list = new ArrayList<CarAppointment>();
	    	list.add(c1);
	    	list.add(c2);
	    	given(carAppointmentService.getAllAppointmentByStartDateRangeSortByPrice(any(), any())).willReturn(list);
	    	mvc.perform(get("/api/carappointments/sortappointmentsbytimeprice?startTime=2010-06-26 05:48:19&endTime=2020-06-26 05:48:19"))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$", hasSize(2)));
	    }
	    
	    @Test
	    public void givenInvalidTimeRange_whenSortAppointmentsByTimePrice_thenReturnJSONArray() throws Exception {
	    	given(carAppointmentService.getAllAppointmentByStartDateRangeSortByPrice(any(), any())).willThrow(InvalidDateException.class);
	    	mvc.perform(get("/api/carappointments/sortappointmentsbytimeprice?startTime=2010-06-26 05:48:19&endTime=2020-06-26 05:48:19"))
	    	.andExpect(status().isPreconditionFailed());
	    }
	    
	    @Test
	    public void givenLicensePlate_whenDeleteAppointment_thenReturnOK() throws Exception {
	    	mvc.perform(delete("/api/carappointments/deleteappointment/1234"))
	    	.andExpect(status().isOk());
	    }
	    
	    @Test
	    public void givenCarAppointment_whenCreate_thenReturnOK() throws Exception {
	    	String json = " {\r\n" + 
	    			"\"licensePlate\": \"hahaha\",\r\n" + 
	    			"\"appoinmentStartTime\": \"2019-06-24T03:48:19.070531\",\r\n" + 
	    			"\"appointmentEndTime\": \"2019-06-24T04:48:19.070531\",\r\n" + 
	    			"\"status\": \"Booked\",\r\n" + 
	    			"\"price\": 121\r\n" + 
	    			"}";
	    	CarAppointment c1 = new CarAppointment("1");
	    	given(carAppointmentService.create(any())).willReturn(c1);
	    	mvc.perform(post("/api/carappointments/create").contentType(MediaType.APPLICATION_JSON).content(json))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("licensePlate", is("1")));
	    }
	    
	    @Test
	    public void givenLicensePlate_whenCreateRandom_thenReturnOK() throws Exception {
	    	CarAppointment c1 = new CarAppointment("1");
	    	given(carAppointmentService.create(any())).willReturn(c1);
	    	mvc.perform(post("/api/carappointments/create/{licensePlate}", "1"))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("licensePlate", is("1")));
	    }
	    
	    @Test
	    public void givenLicensePlateWithStatus_whenUpdate_thenReturnOK() throws Exception {
	    	CarAppointment c1 = new CarAppointment("1");
	    	Optional oC = Optional.of(c1);
	    	given(carAppointmentService.getAppointment(any())).willReturn(oC);
	    	given(carAppointmentService.create(any())).willReturn(c1);
	    	mvc.perform(put("/api/carappointments/update/{licensePlate}?status={status}", "1", "booked"))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("licensePlate", is("1")));
	    }
	    
	    @Test
	    public void givenInvalidLicensePlateWithStatus_whenUpdate_thenReturnOK() throws Exception {
	    	Optional oC = Optional.empty();
	    	given(carAppointmentService.getAppointment(any())).willReturn(oC);
	    	mvc.perform(put("/api/carappointments/update/{licensePlate}?status={status}", "1", "booked"))
	    	.andExpect(status().isNotFound());
	    }
}
