package com.bayzdelivery.service;

import java.util.List;

import com.bayzdelivery.model.Delivery;

public interface DeliveryService {

  public Delivery save(Delivery delivery);

  public Delivery findById(Long deliveryId);
  
  public List<Delivery> getAverageComissionEarnedByDeliveryPersons(String startTime, String endTime);
  
}
