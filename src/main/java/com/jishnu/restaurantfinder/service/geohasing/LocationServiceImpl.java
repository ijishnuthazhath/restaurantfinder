package com.jishnu.restaurantfinder.service.geohasing;

import com.jishnu.restaurantfinder.exception.CoordinatesOutOfBoundException;
import com.jishnu.restaurantfinder.service.LocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This is a service that contains the logic to calcuate the geo hash from coordinates.
 * This class uses the standard - one of the standard I guess, algorithms to find geo-hashes of coordinates
 */
@Service
public class LocationServiceImpl implements LocationService {

    // This is the max limit for coordinates.
    @Value("${geo.hashing.quad.limit}")
    private long quadLimit;

    // Base32 encoding chars
    private static final String BASE32_ALPHABET = "0123456789bcdefghjkmnpqrstuvwxyz";
    private static final char[] BASE32_CHARS = BASE32_ALPHABET.toCharArray();

    // This determines the geo-hashing precision and depth
    private static final int precision = 3;

    // We normalize the huge plain into a smaller space.
    private static final double boundsX = 1.0;
    private static final double boundsY = 1.0;

    private static final int depthLimit = precision * 5 / 2;
    private static final double cellSize = boundsX / (double)depthLimit;


    @Override
    public Optional<String> encodeGeohash(long x, long y) {
        if (isCordinatesInvalid(x, y)) {
            throw new CoordinatesOutOfBoundException();
        }

        double normX = x / (double) quadLimit;
        double normY = y / (double) quadLimit;
        return encodeGeohash(normX, normY);
    }

    private Optional<String> encodeGeohash(final double x, final double y) {

        if (x < 0 || y < 0) {
            return Optional.empty();
        }

        String latBin = convertToBinary(x, new double[]{0.0, boundsX});
        String lonBin = convertToBinary(y, new double[]{0.0, boundsY});

        String interwovenBin = interweave(lonBin, latBin);
        String geohash = binaryToBase32(interwovenBin);

        return Optional.of(geohash.substring(0, precision));
    }

    /**
     * This method is to compute all the cells the restaurant can reach. We need this because, we are not selecting restaurants x far away from user,
     * instead we find all the restaurants which has user coordinates within its radius reach.
     * This adds many geo-hashes for a single restaurant.
     *
     * TODO: Find a way to reduce the number of geo hashes per restaurant.
     */
    @Override
    public Set<String> computeAllGeoHashesWithinGivenRadius(long centerX, long centerY, int radius) {

        if (isCordinatesInvalid(centerX, centerY)) {
            throw new CoordinatesOutOfBoundException();
        }

        final Set<String> geoHashes = new HashSet<>();

        int cells = (int) Math.ceil(radius / cellSize);
        for (int dx = -cells; dx <= cells; dx++) {
            for (int dy = -cells; dy <= cells; dy++) {
                double xi = centerX + dx * cellSize;
                double yi = centerY + dy * cellSize;

                if (xi < 0 || yi < 0) {
                    continue;
                }

                if (isWithinRadius(centerX, centerY, xi, yi, radius)) {
                    final double normCenterX = xi / (double) quadLimit;
                    final double normCenterY = yi / (double) quadLimit;

                    final Optional<String> geohashOptional = encodeGeohash(normCenterX, normCenterY);
                    geohashOptional.ifPresent(geoHashes::add);
                }
            }
        }

        return geoHashes;
    }

    final static int[][] neighborOffsets = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1,  0},          {1,  0},
            {-1,  1}, {0,  1}, {1,  1}
    };

    /**
     * This is for the user. This is one of the edge cases of geo-hashing where two close by restaurants could be in different cells.
     * To tackle this, we add a safe-guard of getting neighbouring cells as well.
     */
    @Override
    public Set<String> computeGeoHashesForNeighbours(long x, long y) {
        final Set<String> neighborsGeoHash = new HashSet<>();

        final double xi = x/(double)quadLimit;
        final double yi = y/(double)quadLimit;

        for (final int[] offset : neighborOffsets) {
            final double neighborX = xi + (cellSize * offset[0]);
            final double neighborY = yi + (cellSize * offset[1]);

            if (neighborX >= 0 && neighborX <= 1 && neighborY >= 0 && neighborY <= 1) {
                encodeGeohash(neighborX, neighborY).ifPresent(neighborsGeoHash::add);
            }
        }

        return neighborsGeoHash;
    }

    private static boolean isWithinRadius(double x1, double y1, double x2, double y2, double radius) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return (dx * dx + dy * dy) <= (radius * radius);
    }


    private static String convertToBinary(double value, double[] range) {
        final char[] binary = new char[depthLimit];
        for (int i = 0; i < depthLimit; i++) {
            double mid = (range[0] + range[1]) / 2.0;
            if (value >= mid) {
                binary[i] = '1';
                range[0] = mid;
            } else {
                binary[i] = '0';
                range[1] = mid;
            }
        }
        return new String(binary);
    }

    private static String interweave(String stringX, String stringY) {
        final StringBuilder interwoven = new StringBuilder();
        for (int i = 0; i < stringX.length(); i++) {
            interwoven.append(stringX.charAt(i)).append(stringY.charAt(i));
        }
        return interwoven.toString();
    }

    private static String binaryToBase32(final String binaryStr) {
        final StringBuilder base32Str = new StringBuilder();
        for (int i = 0; i < binaryStr.length(); i += 5) {
            final String chunk = binaryStr.substring(i, Math.min(i + 5, binaryStr.length()));
            int decimalVal = Integer.parseInt(chunk, 2);
            base32Str.append(BASE32_CHARS[decimalVal]);
        }
        return base32Str.toString();
    }

    private boolean isCordinatesInvalid(final long x, final long y) {
        return x > quadLimit || y > quadLimit;
    }
}
