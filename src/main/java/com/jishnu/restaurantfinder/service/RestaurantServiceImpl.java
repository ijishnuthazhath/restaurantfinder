package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.entity.Restaurant;
import com.jishnu.restaurantfinder.entity.RestaurantGeohash;
import com.jishnu.restaurantfinder.model.RestaurantRequest;
import com.jishnu.restaurantfinder.model.RestaurantResponse;
import com.jishnu.restaurantfinder.repository.RestaurantGeohashRepository;
import com.jishnu.restaurantfinder.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantGeohashRepository restaurantGeohashRepository;
    private final LocationService locationService;

    @Override
    @Transactional
    public void createRestaurant(final RestaurantRequest request, final UUID id) {
        final Restaurant restaurant = toEntity(request, id);
        final Set<RestaurantGeohash> restaurantGeohashSet = generateRestaurantGeoHashes(request.getX(), request.getY(), request.getRadius(), id);
        restaurantRepository.save(restaurant);
        restaurantGeohashRepository.saveAll(restaurantGeohashSet);
    }

    @Override
    public void createRestaurants(List<RestaurantRequest> requests) {
        requests.parallelStream().forEach(request -> this.createRestaurant(request, request.getId()));
    }

    @Override
    public RestaurantResponse getRestaurant(final UUID id) {
        final Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        return toResponse(restaurant);
    }

    private Set<RestaurantGeohash> generateRestaurantGeoHashes(final long x, final long y, final int radius, final UUID id) {
        return locationService.computeAllGeoHashesWithinGivenRadius(x, y, radius).stream()
                .map(
                        geoHash -> RestaurantGeohash
                                .builder()
                                .restaurantId(id)
                                .geohash(geoHash)
                                .build()
                ).collect(Collectors.toSet());
    }


    private static RestaurantResponse toResponse(Restaurant restaurant) {

        if (restaurant == null) {
            return null;
        }

        final String coordinates = "x=" + restaurant.getCoordinateX() + ",y=" + restaurant.getCoordinateY();

        return RestaurantResponse.builder()
                .id(restaurant.getId().toString())
                .name(restaurant.getName())
                .type(restaurant.getType())
                .openingHours(restaurant.getOpeningHours())
                .image(restaurant.getImage())
                .coordinates(coordinates)
                .build();
    }


    private Restaurant toEntity(final RestaurantRequest request, final UUID id) {
        int x = request.getX();
        int y = request.getY();

        return Restaurant.builder()
                .id(id)
                .name(request.getName())
                .type(request.getType())
                .openingHours(request.getOpeningHours())
                .image(request.getImage())
                .coordinateX(x)
                .coordinateY(y)
                .deliveryRadius(request.getRadius())
                .build();
    }
}
