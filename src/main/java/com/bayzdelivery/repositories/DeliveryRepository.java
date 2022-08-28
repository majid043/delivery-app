package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

	 public List<Delivery> findByDeliveryManId(Long delivery_man_id);
	 
	 public List<Delivery> findTop3ByStartTimeBetweenOrderByPriceDesc(Instant startTime, Instant endTime);
}
