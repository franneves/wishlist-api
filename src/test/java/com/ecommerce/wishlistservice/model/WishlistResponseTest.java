package com.ecommerce.wishlistservice.model;

import com.ecommerce.wishlistservice.model.dto.response.WishlistResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WishlistResponseTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void wishlistResponse_validBlankId() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    WishlistResponse wishlistResponse =
        new WishlistResponse(" ", "12", productsMap, Instant.now(), Instant.now());

    List<ConstraintViolation<WishlistResponse>> violations =
        validator.validate(wishlistResponse).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals("It is required to inform the Id", violations.get(0).getMessage());
  }

  @Test
  public void wishlistResponse_validBlankClientId() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    WishlistResponse wishlistResponse =
        new WishlistResponse(
            "61d0c20e35b9915c7934b6a1", " ", productsMap, Instant.now(), Instant.now());

    List<ConstraintViolation<WishlistResponse>> violations =
        validator.validate(wishlistResponse).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(
        "It is required to inform the clientId", violations.get(0).getMessage());
  }

  @Test
  public void wishlistResponse_validBlankProductKey() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put(" ", 10);
    WishlistResponse wishlistResponse =
        new WishlistResponse(
            "61d0c20e35b9915c7934b6a1", "12", productsMap, Instant.now(), Instant.now());

    List<ConstraintViolation<WishlistResponse>> violations =
        validator.validate(wishlistResponse).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(
        "Incorrectly formatted product list. A product map is expected where the key "
            + "corresponds to the product key and the value to the quantity of this product.",
        violations.get(0).getMessage());
  }
}
