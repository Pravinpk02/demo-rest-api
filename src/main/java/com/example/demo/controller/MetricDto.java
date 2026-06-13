package com.example.demo.controller;

public class MetricDto {
    private String label;
    private String value;
    private String change;
    private String status;

    public MetricDto() {}

    public MetricDto(String label, String value, String change, String status) {
        this.label = label;
        this.value = value;
        this.change = change;
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
