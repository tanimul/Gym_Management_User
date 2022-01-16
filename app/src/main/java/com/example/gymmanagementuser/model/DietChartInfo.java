package com.example.gymmanagementuser.model;

public class DietChartInfo {
    private String bodyType,dietChartName,dietChartTime,dietKey;

    public DietChartInfo() {
    }

    public DietChartInfo(String bodyType, String dietChartName, String dietChartTime,String dietKey) {
        this.bodyType = bodyType;
        this.dietChartName = dietChartName;
        this.dietChartTime = dietChartTime;
        this.dietKey=dietKey;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDietChartName() {
        return dietChartName;
    }

    public void setDietChartName(String dietChartName) {
        this.dietChartName = dietChartName;
    }

    public String getDietChartTime() {
        return dietChartTime;
    }

    public void setDietChartTime(String dietChartTime) {
        this.dietChartTime = dietChartTime;
    }

    public String getDietKey() {
        return dietKey;
    }

    public void setDietKey(String dietKey) {
        this.dietKey = dietKey;
    }
}
