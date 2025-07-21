package com.jishnu.restaurantfinder.controller;

import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;
import com.jishnu.restaurantfinder.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantSearchController {

    private final RestaurantSearchService restaurantSearchService;

    public RestaurantSearchController(@Qualifier("geoHashSearchService") final RestaurantSearchService restaurantSearchService) {
        this.restaurantSearchService = restaurantSearchService;
    }


    @GetMapping("/locations/search")
    public ResponseEntity<RestaurantSearchServiceResponseData> search(@RequestParam final long x, @RequestParam final long y) {
        return ResponseEntity.ok(restaurantSearchService.search(x, y));
    }
}
