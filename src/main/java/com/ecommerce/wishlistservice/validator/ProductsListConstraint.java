package com.ecommerce.wishlistservice.validator;

import com.ecommerce.wishlistservice.validator.impl.ProductListValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ProductListValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductsListConstraint {
  String message() default "Invalid products list";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
