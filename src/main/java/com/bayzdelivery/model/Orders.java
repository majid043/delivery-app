package com.bayzdelivery.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.bayzdelivery.enums.Devices;
import com.bayzdelivery.enums.OrderStatuses;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Orders implements Serializable {

	private static final long serialVersionUID = 894777588385031L;

	public Orders() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@CreationTimestamp
	@Column(name = "order_time")
	Instant orderTime;
	
	@NotNull
	@Column(name = "device")
	@Enumerated(EnumType.STRING)
	Devices device;
	
	@NotNull
	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	OrderStatuses orderStatus;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "delivery_man_id", referencedColumnName = "id")
	Person deliveryMan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Instant orderTime) {
		this.orderTime = orderTime;
	}

	public Devices getDevice() {
		return device;
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

	public OrderStatuses getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatuses orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Person getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(Person deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", device=");
		builder.append(device);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", deliveryMan=");
		builder.append(deliveryMan);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(deliveryMan, device, id, orderTime, orderStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		return Objects.equals(deliveryMan, other.deliveryMan) && device == other.device && Objects.equals(id, other.id)
				&& Objects.equals(orderTime, other.orderTime) && orderStatus == other.orderStatus;
	}
}