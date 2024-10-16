package com.tboostai_batch.entity.inner_model;

import lombok.Data;

import java.util.List;

@Data
public class FormattedDescription {
    private String originalDescription;
    private List<String> summarized;
    private List<String> extractedFeatures;
}
