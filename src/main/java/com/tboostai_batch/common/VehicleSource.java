package com.tboostai_batch.common;

import lombok.Getter;

@Getter
public enum VehicleSource {

    EBAY(1, "ebay");

    private final int id;
    private final String name;

    VehicleSource(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
