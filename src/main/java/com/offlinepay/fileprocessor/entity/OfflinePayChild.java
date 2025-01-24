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
import java.util.Date;

@Entity
@Table(name = "offlinepay_child")
@Getter
@Setter
@ToString
public class OfflinePayChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger parentId;
    private String ban;
    private String accountNo;
    private BigDecimal amount;
    private Date transactionDate;
    private String businessType;
    private String subId;
    private Boolean postingStatus;
    private BigInteger errorId;
    @CreationTimestamp
    private Timestamp creationTime;
    @UpdateTimestamp
    private Timestamp updateTime;
}
