package com.tboostai_batch.entity.db_model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tax_info")
public class TaxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(length = 10)
    private String taxJurisdictionId;
    @Column(length = 30)
    private String taxType;
    private Boolean shippingAndHandlingTaxed;
    private Boolean includedInPrice;
    private Boolean ebayCollectAndRemitTax;
    @Column(length = 100)
    private String regionName;
    @Column(length = 15)
    private String regionType;
    @Column(length = 10)
    private String regionId;

    // 一个 TaxEntity 对应一个 Vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleBasicInfoEntity vehicle;
}
