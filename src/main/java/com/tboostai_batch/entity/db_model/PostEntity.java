package com.tboostai_batch.entity.db_model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(length = 100)
    private String title;
    @Column(length = 100)
    private String subtitle;

    @Temporal(TemporalType.TIMESTAMP)
    private Date itemCreationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date itemEndDate;

    private String buyingOptions;

    private String itemAffiliateWebUrl;
    private String itemWebUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private VehicleBasicInfoEntity vehicle;
}
