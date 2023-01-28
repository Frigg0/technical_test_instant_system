package com.frg.technical_test_instant_system.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.technical_test_instant_system.configuration.AbstractExceptionConfiguration;
import com.frg.technical_test_instant_system.providers.models.ParkingData;
import com.frg.technical_test_instant_system.providers.services.ParkingProviderDataBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parkings-around-me/api/v1")
public class ParkingAroundController extends AbstractExceptionConfiguration {

    private final ParkingProviderDataBuilderService parkingProviderDataBuilderService;

    @Autowired
    public ParkingAroundController(ParkingProviderDataBuilderService parkingProviderDataBuilderService) {
        this.parkingProviderDataBuilderService = parkingProviderDataBuilderService;
    }

    @GetMapping
    public String getParkingAroundMe(@RequestParam Double latitude, @RequestParam Double longitude) {

        log.info(String.format("Request made: GET with latitude: %s, longitude: %s", latitude, longitude));
        List<ParkingData> parkingDataList = parkingProviderDataBuilderService.getParkingDataNearMe(latitude, longitude);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(parkingDataList);
        } catch (JsonProcessingException e) {
            throw instantTechnicalSysException(String.format("An error has occurred: %s", e.getMessage()), e);
        }
    }
}
