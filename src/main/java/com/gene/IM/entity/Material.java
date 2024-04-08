package com.gene.IM.entity;


public class Material {

  private Integer materialId;
  private Integer num;
  private Integer cumulation;
  private Integer need;
  private java.sql.Timestamp updateDate;


  public Integer getMaterialId() {
    return materialId;
  }

  public void setMaterialId(Integer materialId) {
    this.materialId = materialId;
  }


  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }


  public Integer getCumulation() {
    return cumulation;
  }

  public void setCumulation(Integer cumulation) {
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
