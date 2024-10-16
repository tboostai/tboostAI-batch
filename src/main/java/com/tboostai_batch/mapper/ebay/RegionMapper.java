package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespRegionDTO;
import com.tboostai_batch.entity.inner_model.Region;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    Region toRegion(EbayRespRegionDTO ebayRespRegionDTO);
    List<Region> toRegions(List<EbayRespRegionDTO> ebayRespRegionDTO);
}
