package com.frg.technical_test_instant_system.services;

import com.frg.technical_test_instant_system.providers.external.ParkingProviderService;
import com.frg.technical_test_instant_system.providers.models.ParkingData;
import com.frg.technical_test_instant_system.providers.services.FindParkingProviderLocationService;
import com.frg.technical_test_instant_system.providers.services.ParkingProviderDataBuilderService;
import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;
import com.frg.technical_test_instant_system.utils.DistanceUtil;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class ParkingProviderDataBuilderServiceTest {

    @Mock
    ParkingProviderDataBuilderService parkingProviderDataBuilderService;

    @Mock
    FindParkingProviderLocationService parkingProviderLocationService;

    @Mock
    ParkingProviderService parkingProviderService;

    @Mock
    DistanceUtil distanceUtil;
    @BeforeAll
    public void init() {

        setField(parkingProviderDataBuilderService, "parkingProviderLocationService", parkingProviderLocationService);
        setField(parkingProviderDataBuilderService, "parkingProviderService", parkingProviderService);
        setField(parkingProviderDataBuilderService, "distanceUtil", distanceUtil);
    }

    @Test
    public void getParkingDataNearMe_withAnyLocation_thenExpectABusinessDatalistObject() {
        List<ParkingData> parkingDataList = Collections.singletonList(new ParkingData("La gare", 300, 120, 600));

        when(parkingProviderDataBuilderService.getParkingDataNearMe(any(), any())).thenReturn(parkingDataList);

        when(parkingProviderService.retrieveParkingDataFromProvider(any())).thenReturn(null);

        assertThat(parkingProviderDataBuilderService.getParkingDataNearMe(10.1 , 12.2))
                .hasSize(1)
                .extracting("placesAvailable")
                .containsExactly(120);

    }

    @Test
    public void getParkingDataNearMe_withoutAnyDataFromProvider_thenExpectAnError() {
        when(parkingProviderService.retrieveParkingDataFromProvider(any())).thenReturn(null);

        assertThatThrownBy(() -> parkingProviderDataBuilderService.getParkingDataNearMe(any(), any()))
                .isInstanceOf(InstantSysException.class)
                .hasMessage("API send status code 500");
    }
}
