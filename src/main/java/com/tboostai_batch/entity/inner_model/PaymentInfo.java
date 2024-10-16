package com.tboostai_batch.entity.inner_model;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInfo {
    private String paymentMethodType;
    private List<String> paymentInstructions;
}
