package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespTaxJurisdictionDTO;
import com.tboostai_batch.entity.inner_model.TaxJurisdiction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RegionMapper.class)
public interface TaxJurisdictionMapper {
    TaxJurisdiction toTaxJurisdiction(EbayRespTaxJurisdictionDTO ebayRespTaxJurisdictionDTO);
}
