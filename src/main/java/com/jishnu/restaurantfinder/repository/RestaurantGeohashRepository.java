package com.jishnu.restaurantfinder.repository;

import com.jishnu.restaurantfinder.entity.RestaurantGeohash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RestaurantGeohashRepository extends JpaRepository<RestaurantGeohash, Long> {

    List<RestaurantGeohash> findByGeohash(String geohash);

    List<RestaurantGeohash> findByGeohashIn(Set<String> geohashes);

}
