package com.jishnu.restaurantfinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jishnu.restaurantfinder.RestaurantfinderApplicationTests;
import com.jishnu.restaurantfinder.model.RestaurantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends RestaurantfinderApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreation() throws Exception {
        final UUID id = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23111");
        final String coordinates = "x=" + 3 + ",y=" + 2;

        final RestaurantRequest request = RestaurantRequest.builder()
                .name("Da Jia Le")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates(coordinates)
                .radius(3)
                .build();

        // Act - PUT the restaurant
        mockMvc.perform(put("/locations/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        // Assert - GET visible restaurants from user point inside delivery radius
        mockMvc.perform(get("/locations/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Da Jia Le"))
                .andExpect(jsonPath("$.coordinates").value("x=3,y=2"));
    }
}