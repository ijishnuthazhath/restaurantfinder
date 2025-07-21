package com.jishnu.restaurantfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "restaurant_geohash")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantGeohash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String geohash;

    @Column(name = "restaurant_id", columnDefinition = "UUID")
    private UUID restaurantId;
}
