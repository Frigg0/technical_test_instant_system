package com.frg.technical_test_instant_system.utils;

import com.frg.technical_test_instant_system.providers.models.Coordinates;
import org.springframework.stereotype.Component;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

@Component
public class DistanceUtil {
    // Earth radius, in kilometers
    private static final int EARTH_RADIUS = 6371;

    public double distanceBetween(Coordinates a, Coordinates b) {
        if (a.equals(b)) {
            return 0;
        } else {
            double latARad = toRadians(a.getLatitude());
            double longARad = toRadians(a.getLongitude());
            double latBRad = toRadians(b.getLatitude());
            double longBRad = toRadians(b.getLongitude());

            double distanceRad = acos(
                    sin(latARad) * sin(latBRad)
                            + cos(latARad) * cos(latBRad) * cos(longBRad - longARad)
            );
            return distanceRad * EARTH_RADIUS * 1000;
        }
    }
}
