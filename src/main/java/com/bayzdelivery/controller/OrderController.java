package com.bayzdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bayzdelivery.model.Orders;
import com.bayzdelivery.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(path ="/api/order")
	public ResponseEntity<Orders> createNewOrder(@RequestBody Orders order) {
		Orders orderResp = orderService.save(order);
		if (orderResp != null) {
			return ResponseEntity.ok(orderResp);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = "/api/order/{order-id}")
	public ResponseEntity<Orders> getOrderById(@PathVariable(name="order-id",required=true) Long orderId) {
		Orders order = orderService.findById(orderId);
		if (order != null) {
			return ResponseEntity.ok(order);
		}
		return ResponseEntity.notFound().build();
	}
}
