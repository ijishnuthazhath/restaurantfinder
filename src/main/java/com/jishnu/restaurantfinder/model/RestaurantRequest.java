package com.jishnu.restaurantfinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jishnu.restaurantfinder.exception.CoordinatesInvalidException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    private UUID id;

    @NotBlank(message = "name is required")
    @NotNull(message = "name is required")
    private String name;

    @NotBlank(message = "type is required")
    @NotNull(message = "type is required")
    private String type;

    @JsonProperty("opening-hours")
    private String openingHours;

    private String image;

    @NotBlank(message = "Coordinates is required")
    @NotNull(message = "Coordinates is required")
    private String coordinates;

    private int radius;

    public int getX() {
        if (coordinates == null || !coordinates.contains("x=")) return -1;
        try {
            String[] parts = coordinates.split(",");
            return Integer.parseInt(parts[0].split("=")[1]);
        } catch (final Exception e) {
            throw new CoordinatesInvalidException();
        }
    }

    public int getY() {
        if (coordinates == null || !coordinates.contains("y=")) return -1;
        try {
            String[] parts = coordinates.split(",");
            return Integer.parseInt(parts[1].split("=")[1]);
        } catch (final Exception e) {
            throw new CoordinatesInvalidException();
        }
    }
}
