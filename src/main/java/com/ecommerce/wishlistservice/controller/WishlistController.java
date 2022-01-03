package com.ecommerce.wishlistservice.controller;

import com.ecommerce.wishlistservice.model.dto.request.UpdateProductsRequest;
import com.ecommerce.wishlistservice.model.dto.response.ProductsInWishlistResponse;
import com.ecommerce.wishlistservice.model.dto.response.WishlistResponse;
import com.ecommerce.wishlistservice.service.WishlistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wish-list")
@Validated
public class WishlistController {
  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @ApiOperation(
      value = "Given an objectId representing the Wishlist object, returns the wishlist information.")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Invalid id, must be a non empty string"),
        @ApiResponse(code = 404, message = "No wish list for this id"),
        @ApiResponse(code = 500, message = "Internal server error"),
      })
  @GetMapping("{id}")
  public ResponseEntity<WishlistResponse> getWishlistById(
      @PathVariable("id") @NotBlank(message = "It is required to inform the id") String id) {
    return ResponseEntity.ok(this.wishlistService.getById(id));
  }

  @ApiOperation(
      value = "Given an code representing the customer, returns the wishlist information.")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 400, message = "Invalid clientId, must be a non empty string"),
          @ApiResponse(code = 404, message = "No wish list for this id"),
          @ApiResponse(code = 409, message = "There is more than one wish list associated with this client"),
          @ApiResponse(code = 500, message = "Internal server error"),
      })
  @GetMapping("client/{clientId}")
  public ResponseEntity<WishlistResponse> getWishlistByClientId(
      @PathVariable("clientId") @NotBlank(message = "It is required to inform the clientId")
          String clientId) {
    return ResponseEntity.ok(this.wishlistService.getByClientId(clientId));
  }

  @ApiOperation(value = "Returns if the product informed is on the customer's wish list.")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 400, message = "Invalid clientId or productKey, must be a non empty string"),
          @ApiResponse(code = 404, message = "No wish list for this id"),
          @ApiResponse(code = 409, message = "There is more than one wish list associated with this client"),
          @ApiResponse(code = 500, message = "Internal server error"),
      })
  @GetMapping("client/{clientId}/contains-product/{productKey}")
  public ResponseEntity<Boolean> getIfProductIsOnTheClientWishlist(
      @PathVariable("clientId") @NotBlank(message = "It is required to inform the clientId")
          String clientId,
      @PathVariable("productKey") @NotBlank(message = "It is required to inform the productKey")
          String productKey) {
    return ResponseEntity.ok(
        this.wishlistService.isProductOnTheClientWishlist(clientId, productKey));
  }

  @ApiOperation(
      value = "Adds products to the client's wish list, if it doesn't exist, a new one is created.")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(
            code = 400,
            message = "Invalid product list, has a blank product key or negative quantity"),
        @ApiResponse(
            code = 409,
            message = "There is more than one wish list associated with this client"),
        @ApiResponse(code = 500, message = "Internal server error"),
      })
  @PostMapping(value = "add-products/", produces = "application/json")
  public ResponseEntity<ProductsInWishlistResponse> addProducts(
      @RequestBody @Valid UpdateProductsRequest request) {
    return ResponseEntity.ok(this.wishlistService.addProducts(request));
  }

  @ApiOperation(value = "Remove products from customer's wish list.")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(
              code = 400,
              message = "Invalid product list, has a blank product key or negative quantity"),
          @ApiResponse(
              code = 409,
              message = "There is more than one wish list associated with this client"),
          @ApiResponse(code = 500, message = "Internal server error"),
      })
  @PostMapping(value = "remove-products/", produces = "application/json")
  public ResponseEntity<ProductsInWishlistResponse> removeProducts(
      @RequestBody UpdateProductsRequest request) {
    return ResponseEntity.ok(this.wishlistService.removeProducts(request));
  }
}
