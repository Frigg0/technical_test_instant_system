package com.frg.technical_test_instant_system.providers.services.impl;

import com.frg.technical_test_instant_system.configuration.AbstractExceptionConfiguration;
import com.frg.technical_test_instant_system.providers.services.FindParkingProviderLocationService;
import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@PropertySource(value = {"classpath:static/apiProvider.properties"})
@Service
public class FindParkingProviderLocationServiceImpl extends AbstractExceptionConfiguration implements FindParkingProviderLocationService {

    @Value("${poitiers.parking_location}")
    public String POITIER_PARKING_LOCATION;

    @Value("${poitiers.parking_slots}")
    public String POITIER_PARKING_SLOTS;

    /**
     * Returns a Map containing the adequate URLs to call to get the parking location around you,
     * also the parking slots available in those.
     * @param locationLatitude latitude to the actual location
     * @param locationLongitude longitude to the actual location
     * @return a map with the API URLs to call.
     * @throws InstantSysException if the parameters are incorrect or if there is a technical error.
     */
    public Map<String, String> getCityParkingLocationAndAvailableSlotsURL(Double locationLatitude, Double locationLongitude) throws InstantSysException {

        // Checking step
        if (Objects.isNull(locationLatitude) || Objects.isNull(locationLongitude)) {
            throw instantTechnicalSysException("Expected a location value to retrieve data");
        }

        // Retrieving city name
        String actualCity = retrieveCityFromLocation(locationLatitude, locationLongitude);

        // Get parking API URLs from location
        return retrieveProviderURLFromActualLocation(actualCity);
    }

    /**
     * Allows to return the city name from location data.
     * This city name serves afterward to get the parking data.
     * @param locationLatitude latitude data
     * @param locationLongitude longitude data
     * @return the city from the location data
     */
    private String retrieveCityFromLocation(Double locationLatitude, Double locationLongitude) {

        // Algorithm to implement depending of the location data to find the city where the person stand from
        // Since we only have one solution possible, this is static

        return "Poitiers";
    }

    /**
     * Returns a Map containing as key the parking location API URL and as value the parking slot API URL
     * corresponding of the given parameter.
     * @param locationToFindURLFrom location to get the adequate API URL from.
     * @return a map with the API URLs
     */
    private Map<String, String> retrieveProviderURLFromActualLocation(String locationToFindURLFrom) {

        if(StringUtils.isBlank(locationToFindURLFrom)) {
            throw instantTechnicalSysException("Error while retrieving URL API. Expected a location city name value to retrieve data");
        }

        // Static data since only one API available
        return new HashMap<>() {{
            put(POITIER_PARKING_LOCATION, POITIER_PARKING_SLOTS);
        }};
    }
}