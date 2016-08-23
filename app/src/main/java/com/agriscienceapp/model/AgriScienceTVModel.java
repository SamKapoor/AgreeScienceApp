
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AgriScienceTVModel {

    private int result;
    @SerializedName("detail")
    private List<AgriScienceTVDetailModel> detail = new ArrayList<AgriScienceTVDetailModel>();

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
    public List<AgriScienceTVDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<AgriScienceTVDetailModel> detail) {
        this.detail = detail;
    }

}
