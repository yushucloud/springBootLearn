package com.liang.producer.respository;

import com.liang.producer.domain.Tx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxRepository extends JpaRepository<Tx,String> {
}
