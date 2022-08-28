package com.bayzdelivery.jobs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.repositories.OrderRepository;

@Component
public class DelayedDeliveryNotifier {

	private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);
	@Autowired
	OrderRepository orderRepository;

	/**
	 *  Use this method for the TASK 3
	 */
	@Scheduled(fixedRate = 30000)
	public void checkDelayedDeliveries() {
		// getting open statuses order
		List<Orders> orders = orderRepository.findByOrderStatus(OrderStatuses.O);
		if (null == orders || orders.isEmpty()) {
			return;
		}
		// if found order(s) that are not yet closed, adding maximum allowed delivery time in order time
		Instant expectedExpiryTime = null;
		for (Orders order : orders) {
			expectedExpiryTime = order.getOrderTime().plus(45, ChronoUnit.MINUTES);
			// checking if the order the delayed or not
			if (Instant.now().isAfter(expectedExpiryTime)) {
				// order is delayed, notifying customer support
				notifyCustomerSupport();
				// setting order as delayed in db, so that not picked in next iteration
				order.setOrderStatus(OrderStatuses.D);
				orderRepository.save(order);
			}
		}
	}


	/**
	 * This method should be called to notify customer support team
	 * It just writes notification on console but it may be email or push notification in real.
	 * So that this method should run in an async way.
	 */
	@Async
	public void notifyCustomerSupport() {
		LOG.info("Customer support team is notified!");
	}
}
