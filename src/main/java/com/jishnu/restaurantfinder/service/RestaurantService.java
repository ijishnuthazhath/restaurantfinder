package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.model.RestaurantResponse;
import com.jishnu.restaurantfinder.model.RestaurantRequest;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {
    void createRestaurant(final RestaurantRequest request, final UUID id);

    void createRestaurants(final List<RestaurantRequest> requests);

    RestaurantResponse getRestaurant(final UUID id);
}
