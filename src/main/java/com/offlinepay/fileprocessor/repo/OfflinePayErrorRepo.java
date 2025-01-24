package com.offlinepay.fileprocessor.repo;

import com.offlinepay.fileprocessor.entity.OfflinePayError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OfflinePayErrorRepo extends JpaRepository<OfflinePayError, BigInteger> {
}
