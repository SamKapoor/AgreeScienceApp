package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 8/30/2016.
 */
public class DistrictDetail {

    private List<DistrictModel> detail = new ArrayList<DistrictModel>();
    private int result;

    public List<DistrictModel> getDetail() {
        return detail;
    }

    public void setDetail(List<DistrictModel> detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
