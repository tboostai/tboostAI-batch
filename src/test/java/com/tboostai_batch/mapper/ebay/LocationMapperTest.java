package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespLocationDTO;
import com.tboostai_batch.entity.inner_model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    private LocationMapper locationMapper;

    @BeforeEach
    void setUp() {
        locationMapper = Mappers.getMapper(LocationMapper.class);
    }
    @Test
    void toLocation() {

        EbayRespLocationDTO ebayRespLocationDTO = new EbayRespLocationDTO();
        ebayRespLocationDTO.setCity("city");
        ebayRespLocationDTO.setCountry("country");
        ebayRespLocationDTO.setPostalCode("postalCode");
        ebayRespLocationDTO.setStateOrProvince("stateOrProvince");

        Location location = locationMapper.toLocation(ebayRespLocationDTO);

        assertNotNull(location);
        assertEquals(ebayRespLocationDTO.getCity(), location.getCity());
        assertEquals(ebayRespLocationDTO.getCountry(), location.getCountry());
        assertEquals(ebayRespLocationDTO.getPostalCode(), location.getPostalCode());
        assertEquals(ebayRespLocationDTO.getStateOrProvince(), location.getStateProvince());
    }
}