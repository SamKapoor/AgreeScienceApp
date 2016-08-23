
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BajarBhavYardListModel {

   private int result;

    @SerializedName("detail")
    private List<BajarBhavYardListDetailModel> detail = new ArrayList<BajarBhavYardListDetailModel>();

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
    public List<BajarBhavYardListDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<BajarBhavYardListDetailModel> detail) {
        this.detail = detail;
    }

}
