package com.pharmadesk.backend.pharmacy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicineDTO {
    private Long id;
    private String name;
    private String genericName;
    private String manufacturer;
    private String category;
    private String hsnCode;
    private Double taxPercentage;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getHsnCode() { return hsnCode; }
    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }
    public Double getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(Double taxPercentage) { this.taxPercentage = taxPercentage; }
}

class StockDTO {
    private Long id;
    private String batchNumber;
    private LocalDate expiryDate;
    private Integer quantityAvailable;
    private BigDecimal sellingRate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public Integer getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(Integer quantityAvailable) { this.quantityAvailable = quantityAvailable; }
    public BigDecimal getSellingRate() { return sellingRate; }
    public void setSellingRate(BigDecimal sellingRate) { this.sellingRate = sellingRate; }
}
