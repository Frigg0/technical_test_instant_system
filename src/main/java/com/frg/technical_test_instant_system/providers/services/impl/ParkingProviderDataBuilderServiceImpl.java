package com.frg.technical_test_instant_system.providers.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.technical_test_instant_system.configuration.AbstractExceptionConfiguration;
import com.frg.technical_test_instant_system.providers.external.ParkingProviderService;
import com.frg.technical_test_instant_system.providers.models.AvailabilityResponseRootElement;
import com.frg.technical_test_instant_system.providers.models.Coordinates;
import com.frg.technical_test_instant_system.providers.models.GlobalParkingData;
import com.frg.technical_test_instant_system.providers.models.GlobalResponseRootElement;
import com.frg.technical_test_instant_system.providers.models.ParkingAvailabilitySpotsData;
import com.frg.technical_test_instant_system.providers.models.ParkingData;
import com.frg.technical_test_instant_system.providers.models.ProviderAvailabilityResponseRecord;
import com.frg.technical_test_instant_system.providers.models.ProviderGlobalResponseRecord;
import com.frg.technical_test_instant_system.providers.services.FindParkingProviderLocationService;
import com.frg.technical_test_instant_system.providers.services.ParkingProviderDataBuilderService;
import com.frg.technical_test_instant_system.utils.DistanceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParkingProviderDataBuilderServiceImpl extends AbstractExceptionConfiguration implements ParkingProviderDataBuilderService {

    private final FindParkingProviderLocationService parkingProviderLocationService;
    private final ParkingProviderService parkingProviderService;
    private final DistanceUtil distanceUtil;

    private Coordinates actualPosition;

    @Autowired
    public ParkingProviderDataBuilderServiceImpl(FindParkingProviderLocationService parkingProviderLocationService,
                                                 ParkingProviderService parkingProviderService,
                                                 DistanceUtil distanceUtil) {
        this.parkingProviderLocationService = parkingProviderLocationService;
        this.parkingProviderService = parkingProviderService;
        this.distanceUtil = distanceUtil;
    }

    @Override
    public List<ParkingData> getParkingDataNearMe(Double latitude, Double longitude) {

        actualPosition = new Coordinates(latitude, longitude);

        return retrieveAndBuildBusinessObjectFromParkingProviderData(latitude, longitude);
    }

    /**
     * Retrieve the list of parking data object from the parking provider consumed depending of the given parameters.
     * @param latitude parameter for latitude
     * @param longitude parameter for longitude
     * @return a list of business data object with all the expected data.
     */
    private List<ParkingData> retrieveAndBuildBusinessObjectFromParkingProviderData(Double latitude, Double longitude){

        // Retrieve the URLs to consume depending of the given location
        Map<String, String> URLs = parkingProviderLocationService.getCityParkingLocationAndAvailableSlotsURL(latitude, longitude);
        String globalDataURL = URLs.keySet().stream().findFirst().orElseThrow(() -> instantTechnicalSysException("URI to find global parking data not provided"));
        String availabilityDataURL = URLs.values().stream().findFirst().orElseThrow(() -> instantTechnicalSysException("URI to find availability parking data not provided"));

        List<ParkingData> parkingDataList;

        try {
            // Retrieve data and build data Object
            // Global Data
            HttpResponse<InputStream> globalDataResponse = parkingProviderService.retrieveParkingDataFromProvider(new URI(globalDataURL));
            // Availability slots
            HttpResponse<InputStream> availabilityResponse = parkingProviderService.retrieveParkingDataFromProvider(new URI(availabilityDataURL));

            parkingDataList = buildParkingDataListFromProviderRawResponse(globalDataResponse, availabilityResponse);

        } catch (Exception e) {
            throw instantTechnicalSysException(String.format("A technical error happened : %s", e.getMessage()), e);
        }

        return parkingDataList;
    }

    /**
     * Allows to build the list of business ParkingData object from the provider API response.
     * @param globalDataResponse provider api response of global data
     * @param availabilityResponse provider api response of availability data
     * @return A list of completed ParkingData
     * @throws IOException Error to be thrown if there is any issue with data mapping
     */
    private List<ParkingData> buildParkingDataListFromProviderRawResponse(HttpResponse<InputStream> globalDataResponse,
                                                                          HttpResponse<InputStream> availabilityResponse) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        // Convert Json to Object
        GlobalResponseRootElement globalResponseRootElement = objectMapper.readValue(globalDataResponse.body(), GlobalResponseRootElement.class);
        AvailabilityResponseRootElement availabilityResponseRootElement = objectMapper.readValue(availabilityResponse.body(), AvailabilityResponseRootElement.class);

        // Build the Business Object
        // Flattened the data objects
        List<GlobalParkingData> globalParkingDataList = globalResponseRootElement.getProviderGlobalResponseRecordList().stream()
                .map(ProviderGlobalResponseRecord::getGlobalParkingData)
                .collect(Collectors.toList());

        List<ParkingAvailabilitySpotsData> parkingAvailabilitySpotsData = availabilityResponseRootElement.getProviderAvailabilityResponseRecords().stream()
                .map(ProviderAvailabilityResponseRecord::getParkingAvailabilitySpotsData)
                .collect(Collectors.toList());

        // Start building Parking data business Object with globalParkingDataList
        List<ParkingData> parkingDataListComputed = new ArrayList<>();

        globalParkingDataList.forEach(globalParkingData -> convertToParkingDataBusinessObject(globalParkingData, parkingAvailabilitySpotsData, parkingDataListComputed));

        return parkingDataListComputed;
        }

    /**
     * Allows to create the business object from a based GlobalParkingData giving the name and distance
     * completed by the availability slots of this parking found from their compared geo location point
     * inn the given data list.
     * If there is missing any data to complete the business object, an error is thrown.
     * @param globalParkingDataToConvert base data object
     * @param parkingAvailabilitySpotsData second part of the data object to match from geo points
     * @param parkingDataListToComplete resulted computed list with completed business data objects
     */
    private void convertToParkingDataBusinessObject(GlobalParkingData globalParkingDataToConvert,
                                                    List<ParkingAvailabilitySpotsData> parkingAvailabilitySpotsData,
                                                    List<ParkingData> parkingDataListToComplete) {

        ParkingData parkingData = new ParkingData();

        parkingData.setName(globalParkingDataToConvert.getNom_du_par());

        // Find the availability slots from the same geolocation parking in the parkingAvailabilitySpotsData
        String nameToFindFromList = globalParkingDataToConvert.getNom_du_par();

        // More precise to use geo point but data are corrupted / not always match
//        ParkingAvailabilitySpotsData filteredParkingAvailabilitySlotsData = parkingAvailabilitySpotsData.stream()
//                .filter(slots -> slots.getAvailabilityCoordinates().equals(coordinatesToFindFromList))
//                .findFirst()
//                .orElseThrow(() -> instantSysException("Error from provider, did not find any matching data for availability slots"));

        ParkingAvailabilitySpotsData filteredParkingAvailabilitySlotsData = parkingAvailabilitySpotsData.stream()
                .filter(slots -> slots.getName().equals(nameToFindFromList))
                .findFirst()
                .orElse(null);

        if(filteredParkingAvailabilitySlotsData != null) {
            parkingData.setCapacity(filteredParkingAvailabilitySlotsData.getCapacity());
            parkingData.setPlacesAvailable(filteredParkingAvailabilitySlotsData.getPlacesAvailable());
        }

        // Set up the distance on the business Object
        double distanceCalculated = distanceUtil.distanceBetween(globalParkingDataToConvert.getGeo_point_2d(), actualPosition);
        parkingData.setDistance((int) distanceCalculated);

        parkingDataListToComplete.add(parkingData);
    }
}
