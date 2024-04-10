package com.gene.IM.entity;


public class Device {

  private Integer line;
  private double temp;
  private double hum;
  private double passRate;
  private String status;
  private String mac;
  private String description;


  public Integer getLine() {
    return line;
  }

  public void setLine(Integer line) {
    this.line = line;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }


  public double getHum() {
    return hum;
  }

  public void setHum(double hum) {
    this.hum = hum;
  }


  public double getPassRate() {
    return passRate;
  }

  public void setPassRate(double passRate) {
    this.passRate = passRate;
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
