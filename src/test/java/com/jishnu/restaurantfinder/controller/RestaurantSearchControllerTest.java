package com.jishnu.restaurantfinder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jishnu.restaurantfinder.RestaurantfinderApplicationTests;
import com.jishnu.restaurantfinder.model.RestaurantRequest;
import com.jishnu.restaurantfinder.model.RestaurantSearchResponse;
import com.jishnu.restaurantfinder.model.RestaurantSearchServiceResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RestaurantSearchControllerTest extends RestaurantfinderApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID id1;
    private RestaurantRequest r1;

    private UUID id2;
    private RestaurantRequest r2;

    private UUID id3;
    private RestaurantRequest r3;

    private UUID id4;
    private RestaurantRequest r4;

    private UUID id5;
    private RestaurantRequest r5;

    private UUID id6;
    private RestaurantRequest r6;

    private UUID id7;
    private RestaurantRequest r7;

    private UUID id8;
    private RestaurantRequest r8;

    @BeforeEach
    public void setUp() {
        id1 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23111");
        r1 = RestaurantRequest.builder()
                .name("Da Jia Le 1")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 1 + ",y=" + 1)
                .radius(1)
                .build();

        id2 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23112");
        r2 = RestaurantRequest.builder()
                .name("Da Jia Le 2")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 2 + ",y=" + 2)
                .radius(2)
                .build();

        id3 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23113");
        r3 = RestaurantRequest.builder()
                .name("Da Jia Le 3")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 5 + ",y=" + 5)
                .radius(1)
                .build();

        id4 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23114");
        r4 = RestaurantRequest.builder()
                .name("Da Jia Le 4")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 2 + ",y=" + 3)
                .radius(5)
                .build();

        id5 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23115");
        r5 = RestaurantRequest.builder()
                .name("Da Jia Le 1")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 100 + ",y=" + 100)
                .radius(1)
                .build();

        id6 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23116");
        r6 = RestaurantRequest.builder()
                .name("Da Jia Le 2")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 200 + ",y=" + 200)
                .radius(2)
                .build();

        id7 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23117");
        r7 = RestaurantRequest.builder()
                .name("Da Jia Le 3")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 500 + ",y=" + 500)
                .radius(10)
                .build();

        id8 = UUID.fromString("51e1545c-8b65-4d83-82f9-7fcad4a23118");
        r8 = RestaurantRequest.builder()
                .name("Da Jia Le 4")
                .type("Restaurant")
                .openingHours("10:00AM-11:00PM")
                .image("https://tinyurl.com")
                .coordinates("x=" + 200 + ",y=" + 300)
                .radius(5)
                .build();
    }

    @Test
    public void testSearch() throws Exception {
        mockMvc.perform(put("/locations/{id}", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r1)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r2)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id3.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r3)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id4.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r4)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id5.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r5)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id6.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r6)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id7.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r7)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/locations/{id}", id8.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r8)))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/locations/search")
                        .param("x", "3")
                        .param("y", "2"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();

        RestaurantSearchServiceResponseData response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {
                }
        );

        assertThat(response.getLocations()).isNotNull()
                .hasSize(2)
                .extracting(RestaurantSearchResponse::getId, RestaurantSearchResponse::getName, RestaurantSearchResponse::getCoordinates, RestaurantSearchResponse::getDistance)
                .containsExactly(
                        tuple("51e1545c-8b65-4d83-82f9-7fcad4a23112", "Da Jia Le 2", "x=2,y=2", 1.0),
                        tuple("51e1545c-8b65-4d83-82f9-7fcad4a23114", "Da Jia Le 4", "x=2,y=3", 1.41421)
                );
    }
}
