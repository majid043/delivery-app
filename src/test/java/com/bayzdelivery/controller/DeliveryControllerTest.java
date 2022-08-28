package com.bayzdelivery.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class DeliveryControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	DeliveryController deliveryController;

	@Mock
	DeliveryService deliveryService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build();
	}

	@Test
	public void testDeliveryShouldBeCreated() throws Exception {
		Delivery delivery = new Delivery();

		Person customer = new Person();
		customer.setId(1l);
		delivery.setCustomer(customer);

		Person deliveryMan = new Person();
		customer.setId(2l);
		delivery.setDeliveryMan(deliveryMan);


		Orders orders = new Orders();
		orders.setId(1l);
		delivery.setOrder(orders);

		delivery.setPrice(600d);
		delivery.setDistance(2d);


		when(deliveryService.save(delivery)).thenReturn(delivery);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(delivery))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void testDeliveryShouldNotBeCreated() throws Exception {
		Delivery delivery = new Delivery();

		Person customer = new Person();
		customer.setId(1l);
		delivery.setCustomer(customer);

		Person deliveryMan = new Person();
		customer.setId(2l);
		delivery.setDeliveryMan(deliveryMan);


		Orders orders = new Orders();
		orders.setId(1l);
		delivery.setOrder(orders);

		delivery.setPrice(600d);
		delivery.setDistance(2d);


		when(deliveryService.save(delivery)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(delivery))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}


	@Test
	public void testGetDeliveryById() throws Exception {
		Long deliveryId = 1l;
		Delivery delivery = new Delivery();
		delivery.setId(1l);
		Person customer = new Person();
		customer.setId(1l);
		delivery.setCustomer(customer);

		Person deliveryMan = new Person();
		customer.setId(2l);
		delivery.setDeliveryMan(deliveryMan);


		Orders orders = new Orders();
		orders.setId(1l);
		delivery.setOrder(orders);

		delivery.setPrice(600d);
		delivery.setDistance(2d);


		when(deliveryService.findById(deliveryId)).thenReturn(delivery);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/"+deliveryId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(delivery.getId().intValue())));
	}

}
