package com.ecommerce.wishlistservice;

import com.ecommerce.wishlistservice.repository.WishlistRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = WishlistRepository.class)
public class WishlistServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(WishlistServiceApplication.class, args);
  }
}
