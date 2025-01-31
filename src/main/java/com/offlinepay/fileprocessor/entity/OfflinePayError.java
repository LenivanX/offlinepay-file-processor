package com.offlinepay.fileprocessor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "offlinepay_error")
@Getter
@Setter
@ToString
public class OfflinePayError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger code;
    private String msg;
    @CreationTimestamp
    private Timestamp creationTime;
    @UpdateTimestamp
    private Timestamp updateTime;
}
