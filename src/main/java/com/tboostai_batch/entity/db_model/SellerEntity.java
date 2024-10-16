package com.tboostai_batch.entity.db_model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "seller")
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    private String platform;
    @Column(length = 100)
    private String username;
    @Column(length = 10)
    private String feedbackPercentage;
    private Integer feedbackScore;

}
