package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespTaxJurisdictionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private EbayRespRegionDTO region;
    private String taxJurisdictionId;
}

