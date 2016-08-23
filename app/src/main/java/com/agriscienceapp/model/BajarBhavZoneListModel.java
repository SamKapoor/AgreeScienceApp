
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BajarBhavZoneListModel {

    private int result;

    @SerializedName("detail")
    private List<BajarBhavZoneListDetailModel> detail = new ArrayList<BajarBhavZoneListDetailModel>();

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
    public List<BajarBhavZoneListDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<BajarBhavZoneListDetailModel> detail) {
        this.detail = detail;
    }

}
