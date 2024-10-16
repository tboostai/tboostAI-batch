package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespImageDTO;
import com.tboostai_batch.entity.inner_model.VehicleImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class VehicleImageMapperTest {

    private VehicleImageMapper vehicleImageMapper;

    @BeforeEach
    void setUp() {
        vehicleImageMapper = new VehicleImageMapperImpl();
    }
    @Test
    void toVehicleImage() {
        EbayRespImageDTO ebayRespImageDTO = new EbayRespImageDTO();
        ebayRespImageDTO.setImageUrl("url");
        ebayRespImageDTO.setWidth(100D);
        ebayRespImageDTO.setHeight(200D);
        VehicleImage vehicleImage = vehicleImageMapper.toVehicleImage(ebayRespImageDTO);

        assertNotNull(vehicleImage);
        assertEquals(ebayRespImageDTO.getImageUrl(), vehicleImage.getUrl());
        assertEquals(ebayRespImageDTO.getWidth(), vehicleImage.getWidth());
        assertEquals(ebayRespImageDTO.getHeight(), vehicleImage.getHeight());
    }

    @Test
    void toVehicleImageList() {
    }
}