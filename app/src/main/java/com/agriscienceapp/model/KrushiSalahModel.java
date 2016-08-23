
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class KrushiSalahModel {

    private int result;

    @SerializedName("detail")
    private List<KrushiSalahDetailModel> detail = new ArrayList<KrushiSalahDetailModel>();

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
    public List<KrushiSalahDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<KrushiSalahDetailModel> detail) {
        this.detail = detail;
    }

}
