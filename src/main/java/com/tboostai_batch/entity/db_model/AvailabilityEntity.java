package com.tboostai_batch.entity.db_model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "availability")
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    private String deliveryOptions;

    @Column(length = 100)
    private String estimatedAvailabilityStatus;
    private Integer estimatedAvailableQuantity;
    private Integer estimatedSoldQuantity;
    private Integer estimatedRemainingQuantity;

    @OneToOne(mappedBy = "availability")
    private VehicleBasicInfoEntity vehicleBasicInfo;
}
