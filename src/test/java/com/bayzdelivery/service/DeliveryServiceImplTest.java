package com.bayzdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.bayzdelivery.enums.Devices;
import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.enums.PersonType;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.OrderRepository;
import com.bayzdelivery.repositories.PersonRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
@WebMvcTest
public class DeliveryServiceImplTest {


	@Mock
	DeliveryService deliveryService;

	@Mock
	DeliveryRepository deliveryRepository;

	@Mock
	PersonRepository personRepository;

	@Mock
	OrderRepository orderRepository;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		deliveryService = new DeliveryServiceImpl(deliveryRepository, personRepository, orderRepository);
	}

	@Test
	public void testDeliveryShouldBeCreated() throws Exception {
		Delivery delivery = new Delivery();

		Person customer = new Person();
		customer.setEmail("majid.aziz786@gmail.com");
		customer.setName("majid");
		customer.setId(1l);
		customer.setRegistrationNumber("+923336755600");
		customer.setType(PersonType.C);
		delivery.setCustomer(customer);

		Person deliveryMan = new Person();
		deliveryMan.setId(2l);
		deliveryMan.setEmail("wajid.aziz786@gmail.com");
		deliveryMan.setName("wajid");
		deliveryMan.setRegistrationNumber("+926767455600");
		deliveryMan.setType(PersonType.D);
		delivery.setDeliveryMan(deliveryMan);


		Orders orders = new Orders();
		orders.setId(1l);
		orders.setDevice(Devices.M);
		Person person = new Person();
		person.setId(1l);
		orders.setDeliveryMan(deliveryMan);
		orders.setOrderStatus(OrderStatuses.O);


		delivery.setOrder(orders);

		delivery.setPrice(600d);
		delivery.setDistance(2d);

		when(personRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
		when(personRepository.findById(deliveryMan.getId())).thenReturn(Optional.of(deliveryMan));
		when(orderRepository.findById(orders.getId())).thenReturn(Optional.of(orders));
		when(deliveryRepository.save(delivery)).thenReturn(delivery);

		assertEquals(31.0, deliveryService.save(delivery).getComission().doubleValue());

	}
}