package com.jishnu.restaurantfinder.loader;

import com.jishnu.restaurantfinder.model.RestaurantRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RestaurantData {
    private List<RestaurantRequest> locations;
}
