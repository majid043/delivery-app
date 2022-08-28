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

import com.bayzdelivery.enums.PersonType;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	PersonController personController;

	@Mock
	PersonService personService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}

	@Test
	public void testCustomerShouldBeRegistered() throws Exception {
		Person person = new Person();
		person.setEmail("majid.aziz786@gmail.com");
		person.setName("majid");
		person.setId(1l);
		person.setRegistrationNumber("+923336755600");
		person.setType(PersonType.C);
		when(personService.save(person)).thenReturn(person);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(person))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(person.getName())));
	}

	@Test
	public void testGetCustomerById() throws Exception {
		Long personId = 1l;
		Person person = new Person();
		person.setEmail("majid.aziz786@gmail.com");
		person.setName("majid");
		person.setRegistrationNumber("+923336755600");
		person.setId(1l);
		person.setType(PersonType.C);
		when(personService.findById(personId)).thenReturn(person);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/"+personId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(person.getId().intValue())))
		.andExpect(jsonPath("$.name", is(person.getName())))
		.andExpect(jsonPath("$.email", is(person.getEmail())));
	}

	@Test
	public void testDeliveryManShouldBeRegistered() throws Exception {
		Person person = new Person();
		person.setEmail("wajid.aziz786@gmail.com");
		person.setName("wajid");
		person.setId(1l);
		person.setRegistrationNumber("+926767455600");
		person.setType(PersonType.D);
		when(personService.save(person)).thenReturn(person);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(person))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(person.getName())));
	}


	@Test
	public void testGetDeliveryManById() throws Exception {
		Long personId = 1l;
		Person person = new Person();
		person.setEmail("majid.aziz786@gmail.com");
		person.setName("majid");
		person.setRegistrationNumber("+923336755600");
		person.setId(1l);
		person.setType(PersonType.D);
		when(personService.findById(personId)).thenReturn(person);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/"+personId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(person.getId().intValue())))
		.andExpect(jsonPath("$.name", is(person.getName())))
		.andExpect(jsonPath("$.email", is(person.getEmail())));
	}

	@Test
	public void testGetPersonById() throws Exception {
		Long personId = 1l;

		when(personService.findById(personId)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/person/"+personId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
}
