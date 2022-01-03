package com.ecommerce.wishlistservice.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.wishlistservice.model.dto.response.WishlistResponse;
import com.ecommerce.wishlistservice.model.entity.Wishlist;
import com.ecommerce.wishlistservice.repository.WishlistRepository;
import com.ecommerce.wishlistservice.service.WishlistService;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class WishlistServiceTest {

  @Autowired private WishlistService wishlistService;

  @MockBean private WishlistRepository wishlistRepository;

  @Test
  public void wishListServiceGetById_withCorrespondentEntry() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    Wishlist wishlist =
        new Wishlist("61d0c20e35b9915c7934b6a1", "12", productsMap, Instant.now(), Instant.now());
    when(this.wishlistRepository.findById(anyString())).thenReturn(java.util.Optional.of(wishlist));

    WishlistResponse response = this.wishlistService.getById("123");

    verify(this.wishlistRepository, times(1)).findById(anyString());
    Assertions.assertEquals(wishlist.getId(), response.getId());
    Assertions.assertEquals(wishlist.getClientId(), response.getClientId());
    Assertions.assertEquals(wishlist.getProducts(), response.getProducts());
  }

  @Test
  public void wishListServiceGetById_withEmptyEntry_throwsNotFound() {
    when(this.wishlistRepository.findById(anyString())).thenReturn(Optional.empty());

    ResponseStatusException thrown =
        Assertions.assertThrows(
            ResponseStatusException.class,
            () -> {
              this.wishlistService.getById("123");
            });
    Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    Assertions.assertEquals("Not found: no wish list for this id", thrown.getReason());
    verify(this.wishlistRepository, times(1)).findById(anyString());
  }

  @Test
  public void wishListServiceIsProductOnTheClientWishlist_withoutProductKey() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    Wishlist wishlist =
        new Wishlist("61d0c20e35b9915c7934b6a1", "12", productsMap, Instant.now(), Instant.now());
    when(this.wishlistRepository.findByClientId(anyString())).thenReturn(List.of(wishlist));

    Assertions.assertFalse(wishlistService.isProductOnTheClientWishlist("12", "another-product-key"));
  }

  @Test
  public void wishListServiceIsProductOnTheClientWishlist_withZeroValue() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 0);
    Wishlist wishlist =
        new Wishlist("61d0c20e35b9915c7934b6a1", "12", productsMap, Instant.now(), Instant.now());
    when(this.wishlistRepository.findByClientId(anyString())).thenReturn(List.of(wishlist));

    Assertions.assertFalse(wishlistService.isProductOnTheClientWishlist("12", "product-key"));
  }

  @Test
  public void wishListServiceIsProductOnTheClientWishlist_withProductKeyAndNonZeroValue() {
    HashMap<String, Integer> productsMap = new HashMap<>();
    productsMap.put("product-key", 10);
    Wishlist wishlist =
        new Wishlist("61d0c20e35b9915c7934b6a1", "12", productsMap, Instant.now(), Instant.now());
    when(this.wishlistRepository.findByClientId(anyString())).thenReturn(List.of(wishlist));

    Assertions.assertTrue(wishlistService.isProductOnTheClientWishlist("12", "product-key"));
  }
}
