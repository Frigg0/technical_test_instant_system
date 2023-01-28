package com.frg.technical_test_instant_system.providers.services;

import com.frg.technical_test_instant_system.providers.models.ParkingData;

import java.util.List;

/**
 * Business interface to build data object from provider
 */
public interface ParkingProviderDataBuilderService {

    /**
     * Allows to retrieve a list of business data object build from the provider raw data
     * This data is generated from the given location data point
     * @param latitude coordinate latitude point
     * @param longitude coordinate longitude point
     * @return a completed list of business data object
     */
    List<ParkingData> getParkingDataNearMe(Double latitude, Double longitude);
}
