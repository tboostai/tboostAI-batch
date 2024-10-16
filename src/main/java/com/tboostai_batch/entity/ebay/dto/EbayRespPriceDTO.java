package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespPriceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal value; //Price in current currency
    private String currency; //Current currency
    private BigDecimal convertedFromValue; //Price in original currency
    private String convertedFromCurrency; // Original currency
}
