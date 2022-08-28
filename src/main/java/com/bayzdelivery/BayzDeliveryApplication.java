package com.bayzdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// enable scheduling and async to send notification (task 3)
@EnableScheduling
@EnableAsync
public class BayzDeliveryApplication {
  public static void main(String[] args) {
    SpringApplication.run(BayzDeliveryApplication.class, args);
  }
}
