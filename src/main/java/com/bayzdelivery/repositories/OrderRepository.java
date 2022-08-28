package com.bayzdelivery.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.model.Orders;

@RestResource(exported = false)
public interface OrderRepository extends CrudRepository<Orders, Long> {
	public Orders findByDeliveryManIdAndOrderStatus(Long delivery_man_id, OrderStatuses status);
	public List<Orders> findByOrderStatus(OrderStatuses status);
}
