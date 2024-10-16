package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.TaxEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespTaxDTO;
import com.tboostai_batch.entity.inner_model.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                TaxJurisdictionMapper.class,
                RegionMapper.class
        }
)
public interface TaxMapper {
    Tax toTax(EbayRespTaxDTO ebayRespTaxDTO);
    List<Tax> toTaxes(List<EbayRespTaxDTO> ebayRespTaxDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "taxJurisdictionId", source = "tax.taxJurisdiction.taxJurisdictionId")
    @Mapping(target = "regionName", source = "tax.taxJurisdiction.region.regionName")
    @Mapping(target = "regionType", source = "tax.taxJurisdiction.region.regionType")
    @Mapping(target = "regionId", source = "tax.taxJurisdiction.region.regionId")
    TaxEntity toTaxEntity(Tax tax);

    List<TaxEntity> toTaxEntities(List<Tax> taxes);
}
