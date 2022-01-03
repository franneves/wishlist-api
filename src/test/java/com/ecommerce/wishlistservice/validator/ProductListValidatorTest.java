package com.ecommerce.wishlistservice.validator;

import com.ecommerce.wishlistservice.validator.impl.ProductListValidator;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductListValidatorTest {
  @Test
  public void productsListIsValid_shouldBeFalse_withProductKeyBlank() {
    ProductListValidator productListValidator = new ProductListValidator();
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put(" ", 10);
    Assertions.assertFalse(productListValidator.isValid(productsMap, null));
  }

  @Test
  public void productsListIsValid_shouldBeFalse_withNegativeProductQuantity() {
    ProductListValidator productListValidator = new ProductListValidator();
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", -10);
    Assertions.assertFalse(productListValidator.isValid(productsMap, null));
  }

  @Test
  public void productsListIsValid_shouldBeTrue_withValidProductMap() {
    ProductListValidator productListValidator = new ProductListValidator();
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    Assertions.assertTrue(productListValidator.isValid(productsMap, null));
  }
}
