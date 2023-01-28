package com.frg.technical_test_instant_system.providers;

import com.frg.technical_test_instant_system.providers.external.impl.ParkingProviderServiceImpl;
import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class ParkingProviderServiceTest {

    @Mock
    ParkingProviderServiceImpl parkingProviderService;

    @BeforeAll
    public void init() {
        HttpClient httpClient = mock(HttpClient.class);
        setField(parkingProviderService, "httpClient", httpClient);
    }

    @Test
    public void retrieveParkingDataFromProvider_withAnAPIError_thenExpectException() {

        HttpResponse<InputStream> mockedResponse = mock(HttpResponse.class);
        when(mockedResponse.statusCode()).thenReturn(500);

        when(parkingProviderService.retrieveParkingDataFromProvider(any())).thenReturn(mockedResponse);

        assertThatThrownBy(() -> parkingProviderService.retrieveParkingDataFromProvider(null))
                .isInstanceOf(InstantSysException.class)
                .hasMessage("API send status code 500");
    }

    @Test
    public void retrieveParkingDataFromProvider_withoutAnAPIError_thenExpectData() {

        HttpResponse<InputStream> mockedResponse = mock(HttpResponse.class);
        when(mockedResponse.statusCode()).thenReturn(200);

        when(parkingProviderService.retrieveParkingDataFromProvider(any())).thenReturn(mockedResponse);

        assertEquals(200, parkingProviderService.retrieveParkingDataFromProvider(null).statusCode());
    }
}
