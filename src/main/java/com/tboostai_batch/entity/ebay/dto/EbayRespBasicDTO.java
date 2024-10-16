package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespBasicDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String itemId; 
    private String sellerItemRevision; 
    private String title; 
    private String subtitle; 
    private EbayRespPriceDTO price; 
    private String categoryPath; 
    private String categoryIdPath; 
    private String condition; 
    private String conditionId; 
    private EbayRespLocationDTO itemLocation; 
    private EbayRespImageDTO image; 
    private List<EbayRespImageDTO> additionalImages; 
    private String itemCreationDate; 
    private String itemEndDate; 
    private EbayRespSellerDTO seller; 
    private List<EbayRespEstimatedAvailabilityDTO> estimatedAvailabilities; 
    private EbayRespShipToLocationsDTO shipToLocations; 
    private List<EbayRespTaxDTO> taxes; 
    private List<EbayRespLocalizedAspectDTO> localizedAspects; 
    private Boolean topRatedBuyingExperience; 
    private List<String> buyingOptions; 
    private String itemAffiliateWebUrl; 
    private String itemWebUrl; 
    private String description; 
    private List<EbayRespPaymentMethodDTO> paymentMethods;
    private Boolean enabledForGuestCheckout;
    private Boolean eligibleForInlineCheckout;
    private Integer lotSize;
    private Boolean priorityListing;
    private Boolean adultOnly;
    private String categoryId;
    private String listingMarketplaceId;
    private EbayRespPriceDTO currentBidPrice;
    private Integer bidCount;
    private Boolean reservePriceMet;
    private EbayRespPriceDTO minimumPriceToBid;
    private Integer uniqueBidderCount;
}
