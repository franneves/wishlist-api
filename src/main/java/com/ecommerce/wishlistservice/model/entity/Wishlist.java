package com.ecommerce.wishlistservice.model.entity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("wishlist")
public class Wishlist {

  @Id private String id;
  private String clientId;
  private HashMap<String, Integer> products = new HashMap<>();
  private Instant createdAt;
  private Instant updatedAt;

  public Wishlist() {}

  public Wishlist(
      String id,
      String clientId,
      HashMap<String, Integer> products,
      Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.clientId = clientId;
    this.products = products;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Wishlist(String clientId) {
    this.clientId = clientId;
  }

  public String getId() {
    return id;
  }

  public String getClientId() {
    return clientId;
  }

  public HashMap<String, Integer> getProducts() {
    return products;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void addProducts(HashMap<String, Integer> productsToAdd) {
    for (Map.Entry<String, Integer> product : productsToAdd.entrySet()) {
      Integer productQuantity = this.products.getOrDefault(product.getKey(), 0);
      this.products.put(product.getKey(), productQuantity + product.getValue());
    }
  }

  public void removeProducts(HashMap<String, Integer> productsToDelete) {
    for (Map.Entry<String, Integer> product : productsToDelete.entrySet()) {
      Integer productQuantity = this.products.getOrDefault(product.getKey(), 0);
      this.products.put(product.getKey(), Math.max(0, productQuantity - product.getValue()));
    }
    this.products.values().removeIf(quantity -> quantity == 0);
  }
}
