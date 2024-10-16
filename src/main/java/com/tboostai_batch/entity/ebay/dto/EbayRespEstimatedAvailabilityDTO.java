package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespEstimatedAvailabilityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> deliveryOptions;
    private String estimatedAvailabilityStatus;
    private Integer estimatedAvailableQuantity;
    private Integer estimatedSoldQuantity;
    private Integer estimatedRemainingQuantity;
}
