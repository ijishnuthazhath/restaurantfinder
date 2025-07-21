package com.jishnu.restaurantfinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private String id;

    private String name;

    private String type;

    @JsonProperty("opening-hours")
    private String openingHours;

    private String image;

    private String coordinates;
}
