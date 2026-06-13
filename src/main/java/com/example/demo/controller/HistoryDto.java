package com.example.demo.controller;

public class HistoryDto {
    private String date;
    private String qty;
    private String cost;

    public HistoryDto() {}

    public HistoryDto(String date, String qty, String cost) {
        this.date = date;
        this.qty = qty;
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
