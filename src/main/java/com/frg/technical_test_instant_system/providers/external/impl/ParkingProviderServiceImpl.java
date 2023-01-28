package com.frg.technical_test_instant_system.providers.external.impl;

import com.frg.technical_test_instant_system.configuration.AbstractExceptionConfiguration;
import com.frg.technical_test_instant_system.providers.external.ParkingProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class ParkingProviderServiceImpl extends AbstractExceptionConfiguration implements ParkingProviderService {

    private final HttpClient httpClient;

    @Autowired
    public ParkingProviderServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse<InputStream> retrieveParkingDataFromProvider(URI providerURI) {

        log.info(String.format("Starting to call provider on this URI: %s", providerURI));
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(providerURI).GET().build();

        try {
            HttpResponse<InputStream> inputStreamHttpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

            // error case - not 2xx status code
            if(!resolveHttpStatus(inputStreamHttpResponse.statusCode()).is2xxSuccessful()) {
                throw instantSysException(String.format("API send status code %s", inputStreamHttpResponse.statusCode()));
            }

            // Success case - is 2xx status code
            return inputStreamHttpResponse;

        } catch (Exception e) {
            throw instantSysException("Error with provider API", e);
        }
    }

    /**
     * Allows to resolve the http status. If there is an error, log it
     * @param statusCode to be resolve
     * @return the httpStatus
     */
    private HttpStatus resolveHttpStatus(int statusCode) {
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);

        if(httpStatus == null) {
            throw instantSysException(String.format("Could not resolve status %d", statusCode));
        }

        return httpStatus;
    }
}
