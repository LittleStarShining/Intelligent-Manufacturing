package com.gene.IM.event;

import org.springframework.context.ApplicationEvent;

public class checkMaterial extends ApplicationEvent {
    private double num;
    private int need;
    private int id;

    public double getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public checkMaterial(Object source, Double num, int need, int id) {
        super(source);
        this.num = num;
        this.need = need;
        this.id = id;
    }
}
