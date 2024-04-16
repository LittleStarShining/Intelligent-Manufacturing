package com.gene.IM.DTO;

public class DoingOrder_ds {
    private int lineID;
    private int orderID;
    private int orderSum;
    private int doneSum;
    private float doneRate;

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
    }

    public int getDoneSum() {
        return doneSum;
    }

    public void setDoneSum(int doneSum) {
        this.doneSum = doneSum;
    }

    public float getDoneRate() {
        return doneRate;
    }

    public void setDoneRate(float doneRate) {
        this.doneRate = doneRate;
    }
}
