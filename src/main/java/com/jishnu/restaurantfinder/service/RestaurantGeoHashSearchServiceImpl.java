package com.jishnu.restaurantfinder.service;

import com.jishnu.restaurantfinder.entity.RestaurantGeohash;
import com.jishnu.restaurantfinder.model.RestaurantSearchResponse;
import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;
import com.jishnu.restaurantfinder.repository.RestaurantGeohashRepository;
import com.jishnu.restaurantfinder.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Qualifier("geoHashSearchService")
public class RestaurantGeoHashSearchServiceImpl implements RestaurantSearchService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantGeohashRepository restaurantGeohashRepository;
    private final LocationService locationService;

    @Override
    public RestaurantSearchServiceResponseData search(long ux, long uy) {
        final Optional<String> userGeoHashOptional = locationService.encodeGeohash(ux, uy);
        final Set<String> neibouringHashes = locationService.computeGeoHashesForNeighbours(ux, uy);

        if (userGeoHashOptional.isEmpty()) {
            return RestaurantSearchServiceResponseData.builder().build();
        }

        final Set<String> allHashes = new HashSet<>(neibouringHashes);
        allHashes.add(userGeoHashOptional.get());

        final Set<UUID> restaurantIds = restaurantGeohashRepository.findByGeohashIn(allHashes)
                .stream()
                .map(RestaurantGeohash::getRestaurantId)
                .collect(Collectors.toSet());

        final List<RestaurantSearchResponse> resList =  restaurantRepository.findAllById(restaurantIds)
                .stream()
                .filter(r -> euclideanDistance(r.getCoordinateX(), r.getCoordinateY(), ux, uy) <= r.getDeliveryRadius())
                .map(r -> RestaurantSearchResponse
                        .builder()
                        .id(r.getId().toString())
                        .name(r.getName())
                        .coordinates("x=" + r.getCoordinateX() + ",y=" + r.getCoordinateY())
                        .distance(euclideanDistance(r.getCoordinateX(), r.getCoordinateY(), ux, uy))
                        .build())
                .toList();

        return RestaurantSearchServiceResponseData
                .builder()
                .locations(resList)
                .userLocation("x=" + ux + ",y=" + uy)
                .build();
    }

    public static double euclideanDistance(long x1, long y1, long x2, long y2) {
        final double value = Math.hypot(x1 - x2, y1 - y2);
        return Math.round(value * 100000.0) / 100000.0;
    }
}
