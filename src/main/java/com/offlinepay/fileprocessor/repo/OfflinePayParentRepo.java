package com.offlinepay.fileprocessor.repo;

import com.offlinepay.fileprocessor.entity.OfflinePayParent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface OfflinePayParentRepo extends JpaRepository<OfflinePayParent, BigInteger> {
    Optional<OfflinePayParent> findByFilename(String filename);

    @Transactional
    @Modifying
    @Query(value = "update offlinepay_parent set total_records=?1,total_amount=?2,status=?3 where id=?4", nativeQuery = true)
    void updateRecord(BigInteger totalRecords, BigDecimal totalAmount, String completed, BigInteger id);

    @Transactional
    @Modifying
    @Query(value = "update offlinepay_parent set error_id=?1 where filename=?2", nativeQuery = true)
    void updateError(BigInteger errorId, String filename);
}
