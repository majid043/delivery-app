package com.bayzdelivery.service;

import com.bayzdelivery.model.Orders;

public interface OrderService {

  public Orders save(Orders order);

  public Orders findById(Long orderId);
  
}
