package com.offlinepay.fileprocessor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "offlinepay_parent")
@Getter
@Setter
@ToString
public class OfflinePayParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String filename;
    private BigInteger totalRecords;
    private BigDecimal totalAmount;
    private String stage;
    private String status;
    private Boolean reportStatus;
    private BigInteger errorId;
    @CreationTimestamp
    private Timestamp creationTime;
    @UpdateTimestamp
    private Timestamp updateTime;
}
