package com.gene.IM.entity;


public class Material {

  private long materialId;
  private long num;
  private long cumulation;
  private long need;
  private java.sql.Timestamp updateDate;


  public long getMaterialId() {
    return materialId;
  }

  public void setMaterialId(long materialId) {
    this.materialId = materialId;
  }


  public long getNum() {
    return num;
  }

  public void setNum(long num) {
    this.num = num;
  }


  public long getCumulation() {
    return cumulation;
  }

  public void setCumulation(long cumulation) {
    this.cumulation = cumulation;
  }


  public long getNeed() {
    return need;
  }

  public void setNeed(long need) {
    this.need = need;
  }


  public java.sql.Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(java.sql.Timestamp updateDate) {
    this.updateDate = updateDate;
  }

}
