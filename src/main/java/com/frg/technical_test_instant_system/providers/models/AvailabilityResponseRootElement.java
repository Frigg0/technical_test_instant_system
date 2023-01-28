package com.frg.technical_test_instant_system.providers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityResponseRootElement {

    private Integer nhits;
    @JsonProperty("records")
    private List<ProviderAvailabilityResponseRecord> providerAvailabilityResponseRecords;
}
