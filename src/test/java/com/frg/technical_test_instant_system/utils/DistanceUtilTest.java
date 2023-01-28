package com.frg.technical_test_instant_system.utils;

import com.frg.technical_test_instant_system.providers.models.Coordinates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;

@RunWith(MockitoJUnitRunner.class)
public class DistanceUtilTest {

    @InjectMocks
    DistanceUtil distanceUtil = new DistanceUtil();

    @Test
    public void distanceBetweenTest() {
        Coordinates myPosition = new Coordinates(46.580224, 0.340375);
        Coordinates parkingPosition = new Coordinates(46.58595804860371, 0.3512954265806957);
        double actualDistance = 1050; // In meters

        double returnedDistance = distanceUtil.distanceBetween(parkingPosition, myPosition);

        assertThat(returnedDistance).isCloseTo(actualDistance, withinPercentage(1));
    }
}
