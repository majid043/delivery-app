package com.bayzdelivery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayzdelivery.enums.Devices;
import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.enums.PersonType;
import com.bayzdelivery.exceptions.BadRequestException;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.OrderRepository;
import com.bayzdelivery.repositories.PersonRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PersonRepository personRepository;
	
	public Orders save(Orders order) {
		if (Devices.M != order.getDevice()) {
			throw new BadRequestException("device is not supported: ", order.getDevice());
		}
		Optional<Person> optionalPerson = Optional.empty();
		
		if (null != order.getDeliveryMan()) {
			optionalPerson = personRepository.findById(order.getDeliveryMan().getId());
			if (!optionalPerson.isPresent()) {
				throw new BadRequestException("delivery man not found for id:", order.getDeliveryMan().getId());
			}
			order.setDeliveryMan(optionalPerson.get());
			if (PersonType.D != order.getDeliveryMan().getType()) {
				throw new BadRequestException("delivery man type is not valid: ", order.getDeliveryMan().getType());
			}
		}
		
		Orders openOrder = orderRepository.findByDeliveryManIdAndOrderStatus(order.getDeliveryMan().getId(), OrderStatuses.O);
		if (null != openOrder) {
			throw new BadRequestException("delivery man is not allowed to deliver multiple orders at the same time: ", order.getDeliveryMan().getId());
		}
		return orderRepository.save(order);
	}

	public Orders findById(Long orderId) {
		Optional<Orders> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get();
		} else {
			return null;
		}
	}
}