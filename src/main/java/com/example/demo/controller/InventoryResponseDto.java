package com.example.demo.controller;

import java.util.List;

public class InventoryResponseDto {
    private List<MetricDto> metrics;
    private List<InventoryProductDto> data;
    private PaginationDto pagination;

    public InventoryResponseDto() {}

    public InventoryResponseDto(List<MetricDto> metrics, List<InventoryProductDto> data, PaginationDto pagination) {
        this.metrics = metrics;
        this.data = data;
        this.pagination = pagination;
    }

    public List<MetricDto> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MetricDto> metrics) {
        this.metrics = metrics;
    }

    public List<InventoryProductDto> getData() {
        return data;
    }

    public void setData(List<InventoryProductDto> data) {
        this.data = data;
    }

    public PaginationDto getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }
}
