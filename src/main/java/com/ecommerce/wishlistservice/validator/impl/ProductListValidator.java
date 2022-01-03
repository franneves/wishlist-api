package com.ecommerce.wishlistservice.validator.impl;

import com.ecommerce.wishlistservice.validator.ProductsListConstraint;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductListValidator implements
    ConstraintValidator<ProductsListConstraint, HashMap<String, Integer>> {

  @Override
  public void initialize(ProductsListConstraint contactNumber) {
  }

  @Override
  public boolean isValid(HashMap<String, Integer> products,
      ConstraintValidatorContext cxt) {
    if(products == null && products.isEmpty()){
      return false;
    }

    for (Map.Entry<String, Integer> product : products.entrySet()) {
      if(product.getKey().isBlank()) {
        return false;
      }
      if(product.getValue() < 0 || product.getValue() > 20) {
        return false;
      }
    }
     return true;
  }
}
