package com.bayzdelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;

@RestController
public class DeliveryController {

	@Autowired
	DeliveryService deliveryService;

	@PostMapping(path ="/api/delivery")
	public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
		Delivery deliveryResp = deliveryService.save(delivery);
		if (deliveryResp != null) {
			return ResponseEntity.ok(deliveryResp);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = "/api/delivery/{delivery-id}")
	public ResponseEntity<Delivery> getDeliveryById(@PathVariable(name="delivery-id",required=true) Long deliveryId) {
		Delivery delivery = deliveryService.findById(deliveryId);
		if (delivery != null) {
			return ResponseEntity.ok(delivery);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path = "/api/delivery/{start-time}/{end-time}")
	public ResponseEntity<List<Delivery>> getAverageComissionEarnedByDeliveryPersons(@PathVariable(name="start-time",required=true) String startTime, @PathVariable(name="end-time",required=true) String endTime) {
		return ResponseEntity.ok(deliveryService.getAverageComissionEarnedByDeliveryPersons(startTime, endTime));
	}
		
}