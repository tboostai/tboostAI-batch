package com.tboostai_batch.entity.ebay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbayRespSellerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String feedbackPercentage;
    private Integer feedbackScore;
}
