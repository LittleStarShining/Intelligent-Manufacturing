package com.gene.IM.DTO;

public class DeviceInfo {


    private long line;
    private String status;
    private String mac;
    private String description;

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
