package com.gene.IM.DTO;

public class HistoryReportGraph {
    private int month;
    private int lineID;
    private double totalMonthMoney;
    private int totalMonthNum;


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public double getTotalMonthMoney() {
        return totalMonthMoney;
    }

    public void setTotalMonthMoney(double totalMonthMoney) {
        this.totalMonthMoney = totalMonthMoney;
    }

    public int getTotalMonthNum() {
        return totalMonthNum;
    }

    public void setTotalMonthNum(int totalMonthNum) {
        this.totalMonthNum = totalMonthNum;
    }
}
