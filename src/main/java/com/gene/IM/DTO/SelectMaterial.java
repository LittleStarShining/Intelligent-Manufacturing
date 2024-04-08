package com.gene.IM.DTO;

import java.sql.Timestamp;

public class SelectMaterial {

    private Integer materialId;
    private Integer num;
    private Integer cumulation;
    private Integer need;
    private java.sql.Timestamp updateDate;
    private java.sql.Timestamp updateDateEndTime;

    private java.sql.Timestamp updateDateStartTime;

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

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getUpdateDateEndTime() {
        return updateDateEndTime;
    }

    public void setUpdateDateEndTime(Timestamp updateDateEndTime) {
        this.updateDateEndTime = updateDateEndTime;
    }

    public Timestamp getUpdateDateStartTime() {
        return updateDateStartTime;
    }

    public void setUpdateDateStartTime(Timestamp updateDateStartTime) {
        this.updateDateStartTime = updateDateStartTime;
    }
}
