package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.SellerEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespSellerDTO;
import com.tboostai_batch.entity.inner_model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EbaySellerMapper {
    Seller toSeller(EbayRespSellerDTO ebayRespSellerDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "platform", expression = "java(com.tboostai_batch.common.GeneralConstants.EBAY_PLATFORM)")
    SellerEntity toSellerEntity(Seller seller);
}
