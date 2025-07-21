package com.jishnu.restaurantfinder.controller;

import com.jishnu.restaurantfinder.model.RestaurantRequest;
import com.jishnu.restaurantfinder.model.RestaurantResponse;
import com.jishnu.restaurantfinder.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PutMapping("/locations/{id}")
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantRequest request, @PathVariable("id") UUID id) {
        restaurantService.createRestaurant(request, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }
}
