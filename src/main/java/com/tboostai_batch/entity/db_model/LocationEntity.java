package com.tboostai_batch.entity.db_model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "location")
public class LocationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(length = 100)
    private String country;
    @Column(length = 100)
    private String stateProvince;
    @Column(length = 100)
    private String city;
    @Column(length = 150)
    private String street;
    @Column(length = 15, nullable = false)
    private String postalCode;
    @Column(length = 10)
    private String unit;
    private double latitude;
    private double longitude;
}
