package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGS on 3/1/2016.
 */
public class PakPasandCropModel {


    private List<PakPasandCropDetailModel> detail = new ArrayList<PakPasandCropDetailModel>();
    private int result;

    public List<PakPasandCropDetailModel> getDetail() {
        return detail;
    }

    public void setDetail(List<PakPasandCropDetailModel> detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
