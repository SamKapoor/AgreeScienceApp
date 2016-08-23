package com.agriscienceapp.model;

/**
 * Created by IGS on 3/1/2016.
 */
public class PakPasandCropDetailModel {
    int CropId;
    String CropName;
    String Remarks;
    String Status;


    public int getCropId() {
        return CropId;
    }

    public void setCropId(int cropId) {
        CropId = cropId;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
