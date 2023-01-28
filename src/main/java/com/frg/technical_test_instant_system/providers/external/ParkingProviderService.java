package com.frg.technical_test_instant_system.providers.external;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpResponse;

public interface ParkingProviderService {

    /**
     * Allows to retrieve data from the given URL
     * @param providerURI url to retrieve data from
     * @return A generic HttpResponse of provider data.
     */
    HttpResponse<InputStream> retrieveParkingDataFromProvider(URI providerURI);
}
