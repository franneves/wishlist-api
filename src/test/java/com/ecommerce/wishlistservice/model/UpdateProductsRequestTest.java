package com.ecommerce.wishlistservice.model;

import com.ecommerce.wishlistservice.model.dto.request.UpdateProductsRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateProductsRequestTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void updateProductsRequest_removeZeroQuantityEntries() {
    String clientId = "12";
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    productsMap.put("invalid-quantity-key", 0);

    UpdateProductsRequest updateProductsRequest = new UpdateProductsRequest(clientId, productsMap);

    Assertions.assertEquals(clientId, updateProductsRequest.getClientId());
    Assertions.assertEquals(1, updateProductsRequest.getProductsToBeUpdated().size());
    Assertions.assertTrue(
        updateProductsRequest.getProductsToBeUpdated().containsKey("product-key"));
  }

  @Test
  public void updateProductsRequest_validBlankClientId() {
    String clientId = " ";
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    UpdateProductsRequest updateProductsRequest = new UpdateProductsRequest(clientId, productsMap);

    List<ConstraintViolation<UpdateProductsRequest>> violations =
        validator.validate(updateProductsRequest).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(
        "It is required to inform the clientId", violations.get(0).getMessage());
  }

  @Test
  public void updateProductsRequest_validBlankProductKey() {
    String clientId = "12";
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put(" ", 10);
    UpdateProductsRequest updateProductsRequest = new UpdateProductsRequest(clientId, productsMap);

    List<ConstraintViolation<UpdateProductsRequest>> violations =
        validator.validate(updateProductsRequest).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(
        "Incorrectly formatted product list. A product map is expected where the key "
            + "corresponds to the product key and the value to the quantity of this product.",
        violations.get(0).getMessage());
  }
}
