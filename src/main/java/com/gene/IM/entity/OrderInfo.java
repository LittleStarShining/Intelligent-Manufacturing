package com.gene.IM.entity;

import java.time.LocalDate;
import java.util.Date;

public class OrderInfo {
    private int orderID;
    private int type;
    private String typeName;
    private LocalDate orderTime;
    private LocalDate ddl;
    private int sumTime;
    private int remainTime;
    private int orderNum;
    private double orderMoney;
    private double progress;
    private String status;
    private int lineID;

    public LocalDate getPredictStart() {
        return predictStart;
    }

    public void setPredictStart(LocalDate predictStart) {
        this.predictStart = predictStart;
    }

    public LocalDate getPredictFinish() {
        return predictFinish;
    }

    public void setPredictFinish(LocalDate predictFinish) {
        this.predictFinish = predictFinish;
    }

    private LocalDate predictStart;
    private LocalDate predictFinish;


    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    private double priority;


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDate getDdl() {
        return ddl;
    }

    public void setDdl(LocalDate ddl) {
        this.ddl = ddl;
    }

    public int getSumTime() {
        return sumTime;
    }

    public void setSumTime(int sumTime) {
        this.sumTime = sumTime;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }
}
