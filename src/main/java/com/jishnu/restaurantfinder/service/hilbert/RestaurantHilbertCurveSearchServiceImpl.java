package com.jishnu.restaurantfinder.service.hilbert;

import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;
import com.jishnu.restaurantfinder.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("hilbertCurveSearchService")
public class RestaurantHilbertCurveSearchServiceImpl implements RestaurantSearchService {
    @Override
    public RestaurantSearchServiceResponseData search(long ux, long uy) {
        throw new RuntimeException("Not implemented yet");
    }
}
