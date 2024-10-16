package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespSellerDTO;
import com.tboostai_batch.entity.inner_model.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SellerMapperTest {

    private EbaySellerMapper ebaySellerMapper;

    @BeforeEach
    void setUp() {
        ebaySellerMapper = Mappers.getMapper(EbaySellerMapper.class);
    }
    @Test
    void toSeller() {
        EbayRespSellerDTO ebayRespSellerDTO = new EbayRespSellerDTO();
        ebayRespSellerDTO.setUsername("username");
        ebayRespSellerDTO.setFeedbackPercentage("65.65%");
        ebayRespSellerDTO.setFeedbackScore(2000);

        Seller  seller = ebaySellerMapper.toSeller(ebayRespSellerDTO);

        assertNotNull(seller);
        assertEquals(ebayRespSellerDTO.getUsername(), seller.getUsername());
        assertEquals(ebayRespSellerDTO.getFeedbackPercentage(), seller.getFeedbackPercentage());
        assertEquals(ebayRespSellerDTO.getFeedbackScore(), seller.getFeedbackScore());
    }
}