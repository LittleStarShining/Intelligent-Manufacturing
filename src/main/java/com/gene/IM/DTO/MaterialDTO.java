package com.gene.IM.DTO;

public class MaterialDTO {
    private Integer materialId;
    private Double quantity;

    private Double cumulation;

    private Double need;

    private Double infer;

    private Integer year;

    private Integer month;

    private String name;

    private Double previousConsume;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need) {
        this.need = need;
    }

    public Double getInfer() {
        return infer;
    }

    public void setInfer(Double infer) {
        this.infer = infer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getCumulation() {
        return cumulation;
    }

    public void setCumulation(Double cumulation) {
        this.cumulation = cumulation;
    }

    public Double getPreviousConsume() {
        return previousConsume;
    }

    public void setPreviousConsume(Double previousConsume) {
        this.previousConsume = previousConsume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
