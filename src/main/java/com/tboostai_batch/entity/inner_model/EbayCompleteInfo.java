package com.tboostai_batch.entity.inner_model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EbayCompleteInfo {

    private List<Availability> availability;
    private EbayAdditionalInfo additionalInfo;
    private Location location;
    private List<PaymentInfo> paymentInfo;
    private List<Region> regionIncluded;
    private List<Region> regionExcluded;
    private Seller seller;
    private List<Tax> tax;
    private VehicleBasicInfo vehicleBasicInfo;
    private VehicleImage vehicleImage;
    private VehiclePostInfo vehiclePostInfo;
    List<VehiclePrice> vehiclePrices;
}
