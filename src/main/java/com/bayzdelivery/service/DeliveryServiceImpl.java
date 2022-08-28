package com.bayzdelivery.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayzdelivery.enums.OrderStatuses;
import com.bayzdelivery.enums.PersonType;
import com.bayzdelivery.exceptions.BadRequestException;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.OrderRepository;
import com.bayzdelivery.repositories.PersonRepository;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	OrderRepository orderRepository;

	public DeliveryServiceImpl() {
	  
	   }
	
	 public DeliveryServiceImpl(DeliveryRepository deliveryRepository, PersonRepository personRepository, OrderRepository orderRepository) {
	      this.deliveryRepository = deliveryRepository;
	      this.personRepository = personRepository;
	      this.orderRepository = orderRepository;
	   }
	
	public Delivery save(Delivery delivery) {
		Optional<Person> optionalPerson = Optional.empty();
		if (null != delivery.getCustomer()) {
			optionalPerson = personRepository.findById(delivery.getCustomer().getId());
			if (!optionalPerson.isPresent()) {
				throw new BadRequestException("customer not found for id:", delivery.getCustomer().getId());
			}
			delivery.setCustomer(optionalPerson.get());

			if (PersonType.C != delivery.getCustomer().getType()) {
				throw new BadRequestException("customer type is not valid: ", delivery.getCustomer().getType());
			}
		}

		if (null != delivery.getDeliveryMan()) {
			optionalPerson = personRepository.findById(delivery.getDeliveryMan().getId());
			if (!optionalPerson.isPresent()) {
				throw new BadRequestException("delivery man not found for id:", delivery.getDeliveryMan().getId());
			}
			delivery.setDeliveryMan(optionalPerson.get());

			if (PersonType.D != delivery.getDeliveryMan().getType()) {
				throw new BadRequestException("delivery man type is not valid: ", delivery.getDeliveryMan().getType());
			}
		}

		if (null != delivery.getOrder()) {
			Optional<Orders> optionalOrder = orderRepository.findById(delivery.getOrder().getId());
			if (!optionalOrder.isPresent()) {
				throw new BadRequestException("order not found for id:", delivery.getOrder().getId());
			}
			delivery.setOrder(optionalOrder.get());
			if (OrderStatuses.O != delivery.getOrder().getOrderStatus()) {
				throw new BadRequestException("order status is not valid for delivery:", delivery.getOrder().getOrderStatus());
			}
			delivery.getOrder().setOrderStatus(OrderStatuses.C);
		}
		
		if (null != delivery.getOrder() && null != delivery.getDeliveryMan()
				&& !delivery.getOrder().getDeliveryMan().getId().equals(delivery.getDeliveryMan().getId())) {
			throw new BadRequestException("order not belongs to delivery man: ", delivery.getDeliveryMan());
		}
		
		if (null != delivery.getCustomer() && null != delivery.getDeliveryMan()
				&& delivery.getCustomer().getId().equals(delivery.getDeliveryMan().getId())) {
			throw new BadRequestException("customer can not be delivery man: ", delivery.getDeliveryMan());
		}

		if (null != delivery.getCustomer() && null != delivery.getDeliveryMan()
				&& delivery.getCustomer().getType().equals(delivery.getDeliveryMan().getType())) {
			throw new BadRequestException("delivery man can not be customer: ", delivery.getDeliveryMan());
		}
		if (null == delivery.getPrice()) {
			delivery.setPrice(0D);
		}
		delivery.setComission(calculateCommision(delivery.getDistance(), delivery.getPrice()));
		if (null != delivery.getOrder()) {
			orderRepository.save(delivery.getOrder());	
		}
		return deliveryRepository.save(delivery);
	}

	private Double calculateCommision(Double distance, Double price) {
		return (price * 0.05) + (distance * 0.5);
	}

	public Delivery findById(Long deliveryId) {
		Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
		if (optionalDelivery.isPresent()) {
			return optionalDelivery.get();
		} else {
			return null;
		}
	}
	
	public List<Delivery> getAverageComissionEarnedByDeliveryPersons(String startTime, String endTime) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
		LocalDate st = LocalDate.parse(startTime, formatter);
		ZonedDateTime localStartTime = st.atStartOfDay(ZoneId.systemDefault());
		LocalDate et = LocalDate.parse(endTime, formatter);
		ZonedDateTime localEndTime = et.atStartOfDay(ZoneId.systemDefault());
		
		List<Delivery> fetchedDeliveryList = deliveryRepository.findTop3ByStartTimeBetweenOrderByPriceDesc(Instant.from(localStartTime), Instant.from(localEndTime));
		if (null == fetchedDeliveryList || fetchedDeliveryList.isEmpty()) {
			return null;
		}
		Map<Long, List<Double>> comissionByDeliveryMan = new ConcurrentHashMap<>();
		List<Double> comissions = null;
		for (Delivery delivery : fetchedDeliveryList) {
			if (null == comissionByDeliveryMan.get(delivery.getDeliveryMan().getId())) {
				comissions = new ArrayList<>();
				comissions.add(delivery.getComission());
				comissionByDeliveryMan.put(delivery.getDeliveryMan().getId(), comissions);
			} else {
				comissionByDeliveryMan.get(delivery.getDeliveryMan().getId()).add(delivery.getComission());
			}
		}
		List<Delivery> finalList = new ArrayList<Delivery>();
		Delivery deliveryMan = null;
		for (Entry<Long, List<Double>> entry : comissionByDeliveryMan.entrySet()) {
			deliveryMan = new Delivery();
			deliveryMan.setDeliveryMan(new Person());
			deliveryMan.getDeliveryMan().setId(entry.getKey());

			OptionalDouble average = entry.getValue()
					.stream()
					.mapToDouble(a -> a)
					.average();
			deliveryMan.setComission(average.isPresent() ? average.getAsDouble() : 0D);
			finalList.add(deliveryMan);
		}
		return finalList;
	}
}
