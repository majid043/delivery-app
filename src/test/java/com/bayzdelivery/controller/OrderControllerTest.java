package com.bayzdelivery.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bayzdelivery.enums.Devices;
import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	OrderController orderController;

	@Mock
	OrderService orderService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	public void testOrderShouldBeRegistered() throws Exception {
		
		Orders orders = new Orders();
		orders.setId(1l);
		orders.setDevice(Devices.M);
		Person person = new Person();
		person.setId(1l);
		orders.setDeliveryMan(person);
		orders.setOrderStatus(OrderStatuses.O);
		
		when(orderService.save(orders)).thenReturn(orders);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(orders))
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(orders.getId().intValue())));
	}
	
	
	@Test
	public void testOrderShouldNotBeRegistered() throws Exception {
		Orders orders = new Orders();
		orders.setId(1l);
		orders.setDevice(Devices.M);
		Person person = new Person();
		person.setId(1l);
		orders.setDeliveryMan(person);
		orders.setOrderStatus(OrderStatuses.O);
		orders.setOrderTime(Instant.now());
		
		
		when(orderService.save(orders)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(person))
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}

	@Test
	public void testGetOrderById() throws Exception {
		Long orderId = 1l;
		Orders orders = new Orders();
		orders.setId(1l);
		orders.setDevice(Devices.M);
		Person person = new Person();
		person.setId(1l);
		orders.setDeliveryMan(person);
		orders.setOrderStatus(OrderStatuses.O);
		orders.setOrderTime(Instant.now());
		
		
		when(orderService.findById(orderId)).thenReturn(orders);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/"+orderId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(orders.getId().intValue())))
	            .andExpect(jsonPath("$.orderStatus", is(orders.getOrderStatus().name())));
	}
	
	@Test
	public void testOrderNotFoundById() throws Exception {
		Long orderId = 1l;
		Orders orders = new Orders();
		orders.setId(1l);
		orders.setDevice(Devices.M);
		Person person = new Person();
		person.setId(1l);
		orders.setDeliveryMan(person);
		orders.setOrderStatus(OrderStatuses.O);
		orders.setOrderTime(Instant.now());
		
		
		when(orderService.findById(orderId)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/"+orderId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
}
