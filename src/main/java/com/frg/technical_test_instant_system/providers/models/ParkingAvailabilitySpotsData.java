package com.frg.technical_test_instant_system.providers.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("fields")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingAvailabilitySpotsData {

    @JsonProperty("capacite")
    private Integer capacity;

    @JsonProperty("places")
    private Integer placesAvailable;

    @JsonProperty("nom")
    private String name;

    @JsonProperty("geo_point_2d")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({ "latitude", "longitude" })
    private Coordinates availabilityCoordinates;

    public Coordinates getAvailabilityCoordinates() {
        return availabilityCoordinates == null ? new Coordinates() : availabilityCoordinates;
    }
}
