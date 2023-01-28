package com.frg.technical_test_instant_system.services;

import com.frg.technical_test_instant_system.providers.services.FindParkingProviderLocationService;
import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class FindParkingProviderLocationServiceTest {

    @Mock
    FindParkingProviderLocationService findParkingProviderLocationService;

    public final String DETAIL_MESSAGE_WRONG_PARAMETERS = "Expected a location value to retrieve data";


    // Tests for method getCityParkingLocationAndSlotsURL
    @Test
    public void whenGetCityParkingLocationAndSlotsURL_withNullParameters_thenExpectAnError() {

        doThrow(new InstantSysException(DETAIL_MESSAGE_WRONG_PARAMETERS)).when(findParkingProviderLocationService).getCityParkingLocationAndAvailableSlotsURL(null, null);

        assertThatThrownBy(() -> findParkingProviderLocationService.getCityParkingLocationAndAvailableSlotsURL(null, null))
                .isInstanceOf(InstantSysException.class)
                .hasMessage(DETAIL_MESSAGE_WRONG_PARAMETERS);
    }
}
