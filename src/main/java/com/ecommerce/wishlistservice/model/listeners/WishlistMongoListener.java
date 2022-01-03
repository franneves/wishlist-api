package com.ecommerce.wishlistservice.model.listeners;

import com.ecommerce.wishlistservice.model.entity.Wishlist;
import java.time.Instant;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class WishlistMongoListener extends AbstractMongoEventListener<Wishlist> {
  @Override
  public void onBeforeConvert(BeforeConvertEvent<Wishlist> event) {
    super.onBeforeConvert(event);
    Instant instant = Instant.now();
    if (event.getSource().getCreatedAt() == null) {
      event.getSource().setCreatedAt(instant);
    }
    event.getSource().setUpdatedAt(instant);
  }
}
