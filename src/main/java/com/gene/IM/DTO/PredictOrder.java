package com.gene.IM.DTO;

import java.util.Date;

public class PredictOrder {
    private int lineID;
    private int orderID;
    private Date predictStart;
    private Date predictFinish;

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

    public Date getPredictStart() {
        return predictStart;
    }

    public void setPredictStart(Date predictStart) {
        this.predictStart = predictStart;
    }

    public Date getPredictFinish() {
        return predictFinish;
    }

    public void setPredictFinish(Date predictFinish) {
        this.predictFinish = predictFinish;
    }
}
