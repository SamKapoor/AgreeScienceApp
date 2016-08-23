
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KrushiSalahAdvisoryModel {

    @SerializedName("detail")
    private int result;
    private List<KrushiSalahAdvisoryDetailModel> detail = new ArrayList<KrushiSalahAdvisoryDetailModel>();

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
    public List<KrushiSalahAdvisoryDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<KrushiSalahAdvisoryDetailModel> detail) {
        this.detail = detail;
    }

}
