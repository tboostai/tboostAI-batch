package com.tboostai_batch.entity.inner_model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EbayAdditionalInfo {

    private String itemId;
    private String sellerItemRevision;
    private String categoryPath;
    private String categoryIdPath;
    private String condition;
    private String conditionId;
    private List<Region> shipRegionIncluded;
    private List<Region> shipRegionExcluded;
    private Boolean topRatedBuyingExperience;
    private Integer lotSize;
    private Boolean priorityListing;
    private Boolean adultOnly;
    private String categoryId;
    private String listingMarketplaceId;
    private VehiclePrice currentBidPrice;
    private Integer bidCount;
    private Boolean reservePriceMet;
    private VehiclePrice minimumPriceToBid;
    private Integer uniqueBidderCount;
    private Boolean enabledForGuestCheckout;
    private Boolean eligibleForInlineCheckout;
}
