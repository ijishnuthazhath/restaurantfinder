package com.jishnu.restaurantfinder.service;

import java.util.Optional;
import java.util.Set;

public interface LocationService {
    Optional<String> encodeGeohash(final long x, final long y);
    Set<String> computeAllGeoHashesWithinGivenRadius(final long x, final long y, int radius);
    Set<String> computeGeoHashesForNeighbours(final long x, final long y);
}
