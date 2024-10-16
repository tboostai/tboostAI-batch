package com.tboostai_batch.component.chunk_components;

import com.tboostai_batch.common.VehiclePriceEnum;
import com.tboostai_batch.config.MapperManager;
import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.entity.inner_model.*;
import com.tboostai_batch.mapper.ebay.*;
import com.tboostai_batch.service.LocationLatLngService;
import com.tboostai_batch.service.VehicleDescriptionService;
import com.tboostai_batch.service.VehicleInfoMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Component
public class EbayProductFetchAndProcessItemProcessor implements ItemProcessor<List<EbayRespBasicDTO>, List<EbayCompleteInfo>> {

    private static final Logger logger = LoggerFactory.getLogger(EbayProductFetchAndProcessItemProcessor.class);

    private final MapperManager mapperManager;

    private final LocationLatLngService locationLatLngService;

    private final VehicleInfoMappingService vehicleInfoMappingService;

    public EbayProductFetchAndProcessItemProcessor(MapperManager mapperManager, LocationLatLngService locationLatLngService, VehicleInfoMappingService vehicleInfoMappingService) {
        this.mapperManager = mapperManager;
        this.locationLatLngService = locationLatLngService;
        this.vehicleInfoMappingService = vehicleInfoMappingService;
    }

    @Override
    public List<EbayCompleteInfo> process(@Nonnull List<EbayRespBasicDTO> ebayRespEntityBasicsJsonDTO) {
        logger.info("Process Product Data Chunk");
        List<EbayCompleteInfo> ebayCompleteInfoList = new ArrayList<>();

        ebayRespEntityBasicsJsonDTO.forEach(ebayRespBasicDTO -> {
            List<Availability> availabilities = mapperManager.getMapper(AvailabilityMapper.class).toAvailabilities(ebayRespBasicDTO.getEstimatedAvailabilities());
            EbayAdditionalInfo ebayAdditionalInfo = mapperManager.getMapper(EbayAdditionalInfoMapper.class).toEbayAdditionalInfo(ebayRespBasicDTO, ebayRespBasicDTO.getShipToLocations());
            Location location = mapperManager.getMapper(LocationMapper.class).toLocation(ebayRespBasicDTO.getItemLocation());
            List<PaymentInfo> paymentInfos = mapperManager.getMapper(PaymentInfoMapper.class).toPaymentInfos(ebayRespBasicDTO.getPaymentMethods());
            List<Tax> taxes = mapperManager.getMapper(TaxMapper.class).toTaxes(ebayRespBasicDTO.getTaxes());
            List<Region> regionsIncluded = mapperManager.getMapper(RegionMapper.class).toRegions(ebayRespBasicDTO.getShipToLocations().getRegionIncluded());
            List<Region> regionsExcluded = mapperManager.getMapper(RegionMapper.class).toRegions(ebayRespBasicDTO.getShipToLocations().getRegionExcluded());
            Seller seller = mapperManager.getMapper(EbaySellerMapper.class).toSeller(ebayRespBasicDTO.getSeller());
            VehiclePostInfo vehiclePostInfo = mapperManager.getMapper(VehiclePostInfoMapper.class).toVehiclePostInfo(ebayRespBasicDTO);
            VehicleInfoMapper vehicleInfoMapper = mapperManager.getMapper(VehicleInfoMapper.class);
            VehiclePrice vehicleCurrentBidPrice;
            VehiclePrice vehicleMinimumBidPrice;
            VehiclePrice vehiclePrice;
            List<VehiclePrice> vehiclePrices = new ArrayList<>();
            if (ebayRespBasicDTO.getCurrentBidPrice() != null) {
                vehicleCurrentBidPrice = mapperManager.getMapper(VehiclePriceMapper.class).toVehiclePrice(ebayRespBasicDTO.getCurrentBidPrice());
                vehicleCurrentBidPrice.setPriceType(VehiclePriceEnum.CURRENT_BID_PRICE);
                vehiclePrices.add(vehicleCurrentBidPrice);
            }
            if (ebayRespBasicDTO.getMinimumPriceToBid() != null) {
                vehicleMinimumBidPrice = mapperManager.getMapper(VehiclePriceMapper.class).toVehiclePrice(ebayRespBasicDTO.getMinimumPriceToBid());
                vehicleMinimumBidPrice.setPriceType(VehiclePriceEnum.MIN_BID_PRICE);
                vehiclePrices.add(vehicleMinimumBidPrice);
            }
            if (ebayRespBasicDTO.getPrice() != null) {
                vehiclePrice = mapperManager.getMapper(VehiclePriceMapper.class).toVehiclePrice(ebayRespBasicDTO.getPrice());
                vehiclePrice.setPriceType(VehiclePriceEnum.PRICE);
                vehiclePrices.add(vehiclePrice);
            }
            VehicleImage vehicleImage = mapperManager.getMapper(VehicleImageMapper.class).toVehicleImage(ebayRespBasicDTO.getImage());

            // Map data to VehicleBasicInfo
            VehicleBasicInfo vehicleBasicInfo = vehicleInfoMapper.toVehicleBasicInfo(ebayRespBasicDTO);
            vehicleInfoMapper.mapLocalizedAspects(vehicleBasicInfo, ebayRespBasicDTO, vehicleInfoMappingService);

            // Get latitude and longitude by specific address(postal code or address)
            locationLatLngService.fetchAndSaveLatLng(location);

            EbayCompleteInfo ebayCompleteInfo = EbayCompleteInfo.builder()
                    .additionalInfo(ebayAdditionalInfo)
                    .location(location)
                    .seller(seller)
                    .paymentInfo(paymentInfos)
                    .tax(taxes)
                    .availability(availabilities)
                    .regionIncluded(regionsIncluded)
                    .regionExcluded(regionsExcluded)
                    .vehiclePostInfo(vehiclePostInfo)
                    .vehicleImage(vehicleImage)
                    .vehicleBasicInfo(vehicleBasicInfo)
                    .vehiclePrices(vehiclePrices)
                    .build();

            ebayCompleteInfoList.add(ebayCompleteInfo);
        });
        return ebayCompleteInfoList;
    }
}