package com.example.demo.repository;

import com.example.demo.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer> {
    List<ProductHistory> findByProductId(String productId);
    List<ProductHistory> findByProductIdOrderByTransactionDateDesc(String productId);
}
