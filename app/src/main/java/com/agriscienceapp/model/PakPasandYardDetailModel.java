package com.agriscienceapp.model;

/**
 * Created by IGS on 3/1/2016.
 */
public class PakPasandYardDetailModel {


    int YardId;
    String YardName;
    String CropIdList;
    int ZoneId;


    public int getYardId() {
        return YardId;
    }

    public void setYardId(int yardId) {
        YardId = yardId;
    }

    public String getYardName() {
        return YardName;
    }

    public void setYardName(String yardName) {
        YardName = yardName;
    }

    public String getCropIdList() {
        return CropIdList;
    }

    public void setCropIdList(String cropIdList) {
        CropIdList = cropIdList;
    }

    public int getZoneId() {
        return ZoneId;
    }

    public void setZoneId(int zoneId) {
        ZoneId = zoneId;
    }
}
