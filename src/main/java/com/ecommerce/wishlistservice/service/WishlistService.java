package com.ecommerce.wishlistservice.service;

import com.ecommerce.wishlistservice.model.dto.request.UpdateProductsRequest;
import com.ecommerce.wishlistservice.model.dto.response.ProductsInWishlistResponse;
import com.ecommerce.wishlistservice.model.dto.response.WishlistResponse;
import com.ecommerce.wishlistservice.model.entity.Wishlist;
import com.ecommerce.wishlistservice.repository.WishlistRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishlistService {
  private final WishlistRepository wishlistRepository;

  public WishlistService(WishlistRepository wishlistRepository) {
    this.wishlistRepository = wishlistRepository;
  }

  public WishlistResponse getById(String id) {
    Optional<Wishlist> wishlist = this.wishlistRepository.findById(id);
    if (wishlist.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Not found: no wish list for this id");
    }
    return new WishlistResponse(wishlist.get());
  }

  public WishlistResponse getByClientId(String clientId) {
    List<Wishlist> clientWishlists = this.wishlistRepository.findByClientId(clientId);
    if (clientWishlists == null || clientWishlists.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Not found: no wish list for this id");
    }
    if (clientWishlists.size() > 1) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Conflicting data: there is more than one wish list associated with this client");
    }
    return new WishlistResponse(clientWishlists.get(0));
  }

  private Wishlist findOrCreateByClientId(String clientId) {
    List<Wishlist> clientWishlists = this.wishlistRepository.findByClientId(clientId);
    if (clientWishlists.isEmpty()) {
      Wishlist wishlist = this.wishlistRepository.save(new Wishlist(clientId));
      clientWishlists.add(wishlist);
    }
    if (clientWishlists.size() > 1) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Conflicting data: there is more than one wish list associated with this client");
    }
    return clientWishlists.get(0);
  }

  private void validateWishlistSize(
      HashMap<String, Integer> currentProducts, HashMap<String, Integer> productsToInsert) {
    Integer quantityProductToInsert = currentProducts.values().stream().mapToInt(i -> i).sum();
    Integer quantityProductInWishList = productsToInsert.values().stream().mapToInt(i -> i).sum();
    if (quantityProductInWishList + quantityProductToInsert > 20) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Invalid request: Attempting to add more products than allowed (maximum per wishlist is 20)");
    }
  }

  public ProductsInWishlistResponse addProducts(UpdateProductsRequest updateProductsRequest) {
    Wishlist clientWishlist = findOrCreateByClientId(updateProductsRequest.getClientId());
    validateWishlistSize(
        clientWishlist.getProducts(), updateProductsRequest.getProductsToBeUpdated());
    clientWishlist.addProducts(updateProductsRequest.getProductsToBeUpdated());
    return new ProductsInWishlistResponse(
        this.wishlistRepository.save(clientWishlist).getProducts());
  }

  public ProductsInWishlistResponse removeProducts(UpdateProductsRequest updateProductsRequest) {
    Wishlist clientWishlist = findOrCreateByClientId(updateProductsRequest.getClientId());
    clientWishlist.removeProducts(updateProductsRequest.getProductsToBeUpdated());
    return new ProductsInWishlistResponse(
        this.wishlistRepository.save(clientWishlist).getProducts());
  }

  public Boolean isProductOnTheClientWishlist(String clientId, String productKey) {
    WishlistResponse wishlistResponse = this.getByClientId(clientId);
    return wishlistResponse.getProducts().containsKey(productKey)
        && wishlistResponse.getProducts().get(productKey) > 0;
  }
}
