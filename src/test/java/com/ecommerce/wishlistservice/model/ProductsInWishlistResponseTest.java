package com.ecommerce.wishlistservice.model;

import com.ecommerce.wishlistservice.model.dto.response.ProductsInWishlistResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductsInWishlistResponseTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void productsInWishlistResponse_getProductsWithValidEntry() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("products-key", 10);
    ProductsInWishlistResponse productsInWishlistResponse =
        new ProductsInWishlistResponse(productsMap);

    List<ConstraintViolation<ProductsInWishlistResponse>> violations =
        validator.validate(productsInWishlistResponse).stream().collect(Collectors.toList());

    Assertions.assertTrue(violations.isEmpty());
    Assertions.assertEquals(productsMap, productsInWishlistResponse.getProducts());
  }

  @Test
  public void productsInWishlistResponse_validBlankProductKey() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put(" ", 10);
    ProductsInWishlistResponse productsInWishlistResponse =
        new ProductsInWishlistResponse(productsMap);

    List<ConstraintViolation<ProductsInWishlistResponse>> violations =
        validator.validate(productsInWishlistResponse).stream().collect(Collectors.toList());

    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(
        "Incorrectly formatted product list. A product map is expected where the key "
            + "corresponds to the product key and the value to the quantity of this product.",
        violations.get(0).getMessage());
  }
}
