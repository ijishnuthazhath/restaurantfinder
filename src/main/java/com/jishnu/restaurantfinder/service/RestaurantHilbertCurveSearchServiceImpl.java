package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;

public class RestaurantHilbertCurveSearchServiceImpl implements RestaurantSearchService {
    @Override
    public RestaurantSearchServiceResponseData search(long ux, long uy) {
        return RestaurantSearchServiceResponseData.builder().build();
    }
}
