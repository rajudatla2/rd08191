package com.cardinalfinance.test.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tool {
    @JsonProperty("toolCode")
    private String toolCode;

    @JsonProperty("toolType")
    private String toolType;

    @JsonProperty("brand")
    private String brand;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}