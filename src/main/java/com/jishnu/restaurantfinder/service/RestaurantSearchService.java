package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;

import java.util.List;

public interface RestaurantSearchService {
    RestaurantSearchServiceResponseData search(final long ux, final long uy);
}
