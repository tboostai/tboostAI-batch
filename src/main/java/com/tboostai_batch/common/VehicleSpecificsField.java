package com.tboostai_batch.common;

import lombok.Getter;

@Getter
public enum VehicleSpecificsField {
    MAKE("make", "Make"),
    MODEL("model", "Model"),
    TRIM("trim", "Trim"),
    YEAR("year", "Year"),
    VIN("vin", "VIN (Vehicle Identification Number)"),
    MILEAGE("mileage", "Mileage"),
    EXTERIOR_COLOR("exteriorColor", "Exterior Color"),
    INTERIOR_COLOR("interiorColor", "Interior Color"),
    BODY_TYPE("bodyType", "Body Type"),
    ENGINE_TYPE("engineType", "Fuel Type"),
    ENGINE_INFO("engineInfo", "Engine"),
    ENGINE_SIZE("engineSize", "Engine Size"),
    CYLINDER("cylinder", "Number of Cylinders"),
    CYLINDER_INFO("cylinderInfo", "Cylinders"),
    TRANSMISSION("transmission", "Transmission"),
    DRIVETRAIN("drivetrain", "Drive Type"),
    TITLE("vehicleTitle", "Vehicle Title"),
    WARRANTY("warranty", "Warranty"),
    DOORS("doors", "Doors");

    // 可扩展字段，根据业务需求新增
    private final String fieldName;  // VehicleBasicInfoEntity 中的字段名
    private final String aspectName; // localizedAspects 中的 name 值

    VehicleSpecificsField(String fieldName, String aspectName) {
        this.fieldName = fieldName;
        this.aspectName = aspectName;
    }

}
