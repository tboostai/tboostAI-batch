package com.tboostai_batch.entity.db_model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Table(name = "Vehicle_Basic_Info")
@Entity
public class VehicleBasicInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(length = 50)
    private String make;

    @Column(length = 50)
    private String model;

    @Column
    private int year;

    @Column(length = 50)
    private String trim;

    @Column(length = 20, unique = true)
    private String vin;

    @Column
    private int mileage;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VehiclePriceEntity> price;

    @Column(length = 10)
    private String exteriorColor;

    @Column(length = 10)
    private String interiorColor;

    @Column(length = 15)
    private String bodyType;

    @Column(length = 10)
    private String engineType;

    @Column(precision = 3, scale = 1)
    private BigDecimal engineSize;

    @Column
    private int cylinder;

    @Column(length = 15)
    private String transmission;

    @Column(length = 30)
    private String drivetrain;

    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "location_id")
    private LocationEntity locationEntity;

    @Column(name = "`condition`",length = 10)
    private String condition;

    @Column(length = 50)
    private String engineInfo;

    @Column(length = 50)
    private String cylinderInfo;

    @Column(length = 200)
    private String warranty;

    @Column(length = 20)
    private String vehicleTitle;

    private int capacity;
    private int doors;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "vehicle_feature_mapping",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<VehicleFeatureEntity> features;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleImageEntity> images;

    @Column
    private Timestamp listingDate;

    @Column
    private int sourceId;

    // 一个车对应一个卖家，卖家可以有多个车
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;

    // 一个车可以有多个支付信息
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PaymentInfoEntity> paymentInfos;

    // 一个车可以有多个税务信息
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TaxEntity> taxInfos;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String aiDescription;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", referencedColumnName = "id")
    private AvailabilityEntity availability;

}
