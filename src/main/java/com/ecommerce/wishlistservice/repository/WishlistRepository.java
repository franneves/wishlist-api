package com.ecommerce.wishlistservice.repository;

import com.ecommerce.wishlistservice.model.entity.Wishlist;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {
  List<Wishlist> findByClientId(String clientId);
}
