package com.jishnu.restaurantfinder.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jishnu.restaurantfinder.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
public class DataLoader implements ApplicationRunner {
    private final RestaurantService restaurantService;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/${load.data.file}")
    private Resource jsonFile;

    @Value("${load.data.enabled:false}")
    private boolean loadData;

    public DataLoader(RestaurantService restaurantService, ObjectMapper objectMapper) {
        this.restaurantService = restaurantService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (loadData) {
            final TypeReference<RestaurantData> typeRef = new TypeReference<>() {
            };
            final RestaurantData loadedFromJsonModel = objectMapper.readValue(jsonFile.getInputStream(), typeRef);
            restaurantService.createRestaurants(loadedFromJsonModel.getLocations());
            log.info("✅ Loaded {} restaurants.", loadedFromJsonModel.getLocations().size());
        }
    }
}
