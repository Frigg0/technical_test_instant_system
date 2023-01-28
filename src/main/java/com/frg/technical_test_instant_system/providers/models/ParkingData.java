package com.frg.technical_test_instant_system.providers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingData {

    private String name;
    private Integer capacity;
    private Integer placesAvailable;
    private Integer distance;
}
