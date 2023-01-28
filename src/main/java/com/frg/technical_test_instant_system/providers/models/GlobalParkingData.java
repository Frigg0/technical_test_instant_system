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
public class GlobalParkingData {

    @JsonProperty("nom_du_par")
    private String nom_du_par;

    @JsonProperty("geo_point_2d")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({ "latitude", "longitude" })
    private Coordinates geo_point_2d;

    public Coordinates getGeo_point_2d() {
        return geo_point_2d == null ? new Coordinates() : geo_point_2d;
    }
}
