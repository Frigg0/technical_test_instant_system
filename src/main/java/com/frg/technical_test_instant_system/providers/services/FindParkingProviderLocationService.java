package com.frg.technical_test_instant_system.providers.services;

import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;

import java.util.Map;

/**
 * Business interface to get the external URL from technical data
 */
public interface FindParkingProviderLocationService {

    /**
     * Returns a Map containing the adequate URLs to call to get the parking location around you,
     * also the parking slots available in those.
     * @param locationLatitude latitude to the actual location
     * @param locationLongitude longitude to the actual location
     * @return a map with the API URLs to call.
     * @throws InstantSysException if the parameters are incorrect or if there is a technical error.
     */
    Map<String, String> getCityParkingLocationAndAvailableSlotsURL(Double locationLatitude, Double locationLongitude) throws InstantSysException;
}
