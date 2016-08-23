package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IGS on 3/1/2016.
 */
public class PakPasandYardModel {


    private List<PakPasandYardDetailModel> detail = new ArrayList<PakPasandYardDetailModel>();
    private int result;

    public List<PakPasandYardDetailModel> getDetail() {
        return detail;
    }

    public void setDetail(List<PakPasandYardDetailModel> detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
