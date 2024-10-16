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
public class EbayRespPaymentMethodDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String paymentMethodType;
    private List<String> paymentInstructions;
}
