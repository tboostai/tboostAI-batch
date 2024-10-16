package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespTaxDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private EbayRespTaxJurisdictionDTO taxJurisdiction;
    private String taxType;
    private Boolean shippingAndHandlingTaxed;
    private Boolean includedInPrice;
    private Boolean ebayCollectAndRemitTax;
}
