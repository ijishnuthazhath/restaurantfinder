package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.entity.Restaurant;
import com.jishnu.restaurantfinder.model.RestaurantSearchResponse;
import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;
import com.jishnu.restaurantfinder.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Qualifier("naiveSearchService")
public class RestaurantNaiveSearchServiceImpl implements RestaurantSearchService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantSearchServiceResponseData search(long ux, long uy) {
        final List<Restaurant> allRestaurants = restaurantRepository.findAll();
        final List<RestaurantSearchResponse> resList =  allRestaurants
                .stream()
                .filter(restaurant -> euclideanDistance(restaurant.getCoordinateX(), restaurant.getCoordinateY(), ux, uy) <= restaurant.getDeliveryRadius())
                .map(restaurant ->
                        RestaurantSearchResponse
                                .builder()
                                .id(restaurant.getId().toString())
                                .name(restaurant.getName())
                                .coordinates("x=" + restaurant.getCoordinateX() + ",y=" + restaurant.getCoordinateY())
                                .distance(euclideanDistance(restaurant.getCoordinateX(), restaurant.getCoordinateY(), ux, uy))
                                .build())
                .toList();

        return RestaurantSearchServiceResponseData
                .builder()
                .userLocation("x=" + ux + ",y=" + uy)
                .locations(resList)
                .build();
    }

    public static double euclideanDistance(long x1, long y1, long x2, long y2) {
        final double value = Math.hypot(x1 - x2, y1 - y2);
        return Math.round(value * 100000.0) / 100000.0;
    }
}
