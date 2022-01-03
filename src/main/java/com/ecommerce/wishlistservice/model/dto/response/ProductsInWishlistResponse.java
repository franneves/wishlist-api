package com.ecommerce.wishlistservice.model.dto.response;

import com.ecommerce.wishlistservice.validator.ProductsListConstraint;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;

public class ProductsInWishlistResponse {
  @ApiModelProperty(
      value =
          "A map that represents the products in the client's wishlist,"
              + " where the key is the productID and the value is its quantity",
      example = "{'product-key': 1}")
  @ProductsListConstraint(
      message =
          "Incorrectly formatted product list. A product map is expected where the key corresponds "
              + "to the product key and the value to the quantity of this product.")
  private final HashMap<String, Integer> products;

  public ProductsInWishlistResponse(HashMap<String, Integer> productsInserted) {
    this.products = productsInserted;
  }

  public HashMap<String, Integer> getProducts() {
    return products;
  }

  @Override
  public String toString() {
    return "WishlistProductsResponse{" + "products=" + products + '}';
  }
}
