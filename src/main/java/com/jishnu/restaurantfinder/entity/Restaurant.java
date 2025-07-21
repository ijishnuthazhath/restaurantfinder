package com.jishnu.restaurantfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    private String type;

    @Column(name = "opening_hours")
    private String openingHours;

    private String image;

    @Column(name = "x")
    private long coordinateX;

    @Column(name = "y")
    private long coordinateY;

    @Column(name = "delivery_radius")
    private int deliveryRadius;
}
