package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 8/30/2016.
 */
public class TalukaDetail {
    public List<TalukaModel> getDetail() {
        return detail;
    }

    public void setDetail(List<TalukaModel> detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private List<TalukaModel> detail = new ArrayList<TalukaModel>();
    private int result;
}
