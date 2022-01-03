package com.ecommerce.wishlistservice.model.dto.request;

import com.ecommerce.wishlistservice.validator.ProductsListConstraint;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import javax.validation.constraints.NotBlank;

public class UpdateProductsRequest {

  @ApiModelProperty(value = "Client identification code", example = "12")
  @NotBlank(message = "It is required to inform the clientId")
  private final String clientId;

  @ApiModelProperty(
      value =
          "A map that represents the products, where the key is the productID and the value is its quantity",
      example = "{'product-key': 1}")
  @ProductsListConstraint(
      message =
          "Incorrectly formatted product list. A product map is expected where the key corresponds "
              + "to the product key and the value to the quantity of this product.")
  private final HashMap<String, Integer> productsToBeUpdated;

  public UpdateProductsRequest(String clientId, HashMap<String, Integer> productsToBeUpdated) {
    productsToBeUpdated.values().removeIf(quantity -> quantity == 0);

    this.clientId = clientId;
    this.productsToBeUpdated = productsToBeUpdated;
  }

  public String getClientId() {
    return clientId;
  }

  public HashMap<String, Integer> getProductsToBeUpdated() {
    return productsToBeUpdated;
  }
}
