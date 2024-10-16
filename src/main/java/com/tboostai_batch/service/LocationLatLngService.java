package com.tboostai_batch.service;

import com.google.maps.model.LatLng;
import com.tboostai_batch.entity.db_model.LocationEntity;
import com.tboostai_batch.entity.inner_model.Location;
import com.tboostai_batch.mapper.ebay.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tboostai_batch.common.GeneralConstants.STAR;

@Service
public class LocationLatLngService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleGeocodingService.class);
    private final GoogleGeocodingService googleGeocodingService;

    public LocationLatLngService(GoogleGeocodingService googleGeocodingService) {
        this.googleGeocodingService = googleGeocodingService;
    }

    public void fetchAndSaveLatLng(Location location) {
        logger.info("Start fetchAndSaveLatLng process");
        Mono<LatLng> latLngMono = null;
        if (location != null) {
            if (location.getPostalCode() != null && !location.getPostalCode().contains(STAR)) {
                latLngMono = googleGeocodingService.getLatLngFromAddress(location.getPostalCode());
            } else {
                String address = Stream.of(
                                location.getUnit(),
                                location.getStreet(),
                                location.getCity(),
                                location.getStateProvince(),
                                location.getCountry())
                        .filter(s -> s != null && !s.trim().isEmpty())
                        .collect(Collectors.joining(","));
                logger.info("fetchAndSaveLatLng - Address: {}", address);
                latLngMono = googleGeocodingService.getLatLngFromAddress(address);
            }
        }

        if (latLngMono != null) {
            LatLng latLng = latLngMono.block();
            if (latLng != null) {
                location.setLatitude(latLng.lat);
                location.setLongitude(latLng.lng);
            }
        }
        logger.info("fetchAndSaveLatLng - location with lat and lng: {}", location);
    }
}
