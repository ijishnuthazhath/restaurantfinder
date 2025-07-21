package com.jishnu.restaurantfinder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class RestaurantSearchResponse {
    private final String id;
    private final String name;
    private final String coordinates;
    private final double distance;
}
