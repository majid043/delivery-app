package com.bayzdelivery.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "delivery")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delivery implements Serializable {

	private static final long serialVersionUID = 123765351514001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Column(name = "start_time")
	Instant startTime;

	@NotNull
	@Column(name = "end_time")
	Instant endTime;

	@NotNull
	@Column(name = "distance")
	Double distance;

	@Column(name = "price")
	Double price;

	@Column(name = "comission")
	Double comission;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "delivery_man_id", referencedColumnName = "id")
	Person deliveryMan;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	Person customer;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	Orders order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getComission() {
		return comission;
	}

	public void setComission(Double comission) {
		this.comission = comission;
	}

	public Person getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(Person deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	public Person getCustomer() {
		return customer;
	}

	public void setCustomer(Person customer) {
		this.customer = customer;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comission, customer, deliveryMan, distance, endTime, id, order, price, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delivery other = (Delivery) obj;
		return Objects.equals(comission, other.comission) && Objects.equals(customer, other.customer)
				&& Objects.equals(deliveryMan, other.deliveryMan) && Objects.equals(distance, other.distance)
				&& Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(order, other.order) && Objects.equals(price, other.price)
				&& Objects.equals(startTime, other.startTime);
	}
}