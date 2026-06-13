package com.example.demo.controller;

import com.example.demo.model.PharmaOrder;
import com.example.demo.repository.PharmaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class PharmaOrderController {

    @Autowired
    private PharmaOrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<PharmaOrder>> getAllOrders() {
        List<PharmaOrder> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }
}
