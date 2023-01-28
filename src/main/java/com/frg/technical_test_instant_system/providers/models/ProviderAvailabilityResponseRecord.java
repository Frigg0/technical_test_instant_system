package com.frg.technical_test_instant_system.providers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("records")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderAvailabilityResponseRecord {

    @JsonProperty("fields")
    private ParkingAvailabilitySpotsData parkingAvailabilitySpotsData;
}
