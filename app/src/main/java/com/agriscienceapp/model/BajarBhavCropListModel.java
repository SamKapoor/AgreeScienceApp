
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BajarBhavCropListModel {


    @SerializedName("detail")
    private int result;
    private List<Detail> detail = new ArrayList<Detail>();

    /**
     *
     * @return
     *     The result
     */
    public int getResult() {
        return result;
    }

    /**
     *
     * @param result
     *     The result
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     *
     * @return
     *     The detail
     */
    public List<Detail> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

}
