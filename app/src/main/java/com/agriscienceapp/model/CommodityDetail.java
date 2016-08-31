package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 8/30/2016.
 */
public class CommodityDetail {

    private List<CommodityModel> detail = new ArrayList<CommodityModel>();
    private int result;

    public List<CommodityModel> getDetail() {
        return detail;
    }

    public void setDetail(List<CommodityModel> detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
