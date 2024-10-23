package com.liang.consumer.respository;

import com.liang.consumer.domain.Tx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxRepository extends JpaRepository<Tx,String> {
}
