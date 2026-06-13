package com.example.demo.repository;

import com.example.demo.model.PharmaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmaOrderRepository extends JpaRepository<PharmaOrder, Long> {
}
