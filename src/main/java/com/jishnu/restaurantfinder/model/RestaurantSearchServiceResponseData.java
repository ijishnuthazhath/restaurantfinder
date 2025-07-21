package com.jishnu.restaurantfinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RestaurantSearchServiceResponseData {
    @JsonProperty("user-location")
    private String userLocation;
    private List<RestaurantSearchResponse> locations;
}
