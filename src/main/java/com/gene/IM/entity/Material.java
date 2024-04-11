package com.gene.IM.entity;


public class Material {

  private Integer materialId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;
  private Double num;
  private Double cumulation;
  private Integer need;
  private java.sql.Timestamp updateDate;


  public Integer getMaterialId() {
    return materialId;
  }

  public void setMaterialId(Integer materialId) {
    this.materialId = materialId;
  }


  public Double getNum() {
    return num;
  }

  public void setNum(Double num) {
    this.num = num;
  }


  public Double getCumulation() {
    return cumulation;
  }

  public void setCumulation(Double cumulation) {
    this.cumulation = cumulation;
  }


  public Integer getNeed() {
    return need;
  }

  public void setNeed(Integer need) {
    this.need = need;
  }


  public java.sql.Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(java.sql.Timestamp updateDate) {
    this.updateDate = updateDate;
  }

}
