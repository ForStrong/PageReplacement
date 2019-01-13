package com.zhangsiyuan.pagereplacement;

public class IncomeBean {
    private float fifoValue;
    private float optValue;
    private float lruValue;
    private String dataS;

    public IncomeBean(float fifoValue, float optValue, float lruValue, String dataS) {
        this.fifoValue = fifoValue;
        this.optValue = optValue;
        this.lruValue = lruValue;
        this.dataS = dataS;
    }

    public String getDataS() {
        return dataS;
    }

    public void setDataS(String dataS) {
        this.dataS = dataS;
    }

    public IncomeBean(float fifoValue) {
        this.fifoValue = fifoValue;
    }

    public float getFifoValue() {
        return fifoValue;
    }

    public void setFifoValue(float fifoValue) {
        this.fifoValue = fifoValue;
    }

    public float getOptValue() {
        return optValue;
    }

    public void setOptValue(float optValue) {
        this.optValue = optValue;
    }

    public float getLruValue() {
        return lruValue;
    }

    public void setLruValue(float lruValue) {
        this.lruValue = lruValue;
    }
}
