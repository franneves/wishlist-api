package com.ecommerce.wishlistservice.model.dto.response;

import com.ecommerce.wishlistservice.model.entity.Wishlist;
import com.ecommerce.wishlistservice.validator.ProductsListConstraint;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.util.HashMap;
import javax.validation.constraints.NotBlank;

public class WishlistResponse {
  @ApiModelProperty(value = "objectId representing the Wishlist object", example = "12")
  @NotBlank(message = "It is required to inform the Id")
  private String id;

  @ApiModelProperty(value = "Client identification code", example = "61d20ae070539b48e808dcc0")
  @NotBlank(message = "It is required to inform the clientId")
  private String clientId;

  @ApiModelProperty(
      value =
          "A map that represents the products, where the key is the productID and the value is its quantity",
      example = "{'product-key': 1}")
  @ProductsListConstraint(
      message =
          "Incorrectly formatted product list. A product map is expected where the key corresponds "
              + "to the product key and the value to the quantity of this product.")
  private HashMap<String, Integer> products = new HashMap<>();

  @ApiModelProperty(
      value = "At the time the wish list was created",
      example = "2022-01-02T20:34:16.785Z")
  private Instant createdAt;

  @ApiModelProperty(
      value =
          "At the time the wish list was updated",
      example = "2022-01-02T20:34:16.785Z")
  private Instant updatedAt;

  public WishlistResponse() {
  }

  public WishlistResponse(Wishlist wishlist) {
    this.id = wishlist.getId();
    this.clientId = wishlist.getClientId();
    this.products = wishlist.getProducts();
    this.createdAt = wishlist.getCreatedAt();
    this.updatedAt = wishlist.getUpdatedAt();
  }

  public WishlistResponse(String id, String clientId,
      HashMap<String, Integer> products, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.clientId = clientId;
    this.products = products;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {
    return "WishlistResponse{"
        + "id='"
        + id
        + '\''
        + ", clientId='"
        + clientId
        + '\''
        + ", products="
        + products
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }
}
