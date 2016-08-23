
package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

public class PakPasandModel {

//    @SerializedName("detail")
    private int result;
    private List<PakPasandDetailModel> detail = new ArrayList<PakPasandDetailModel>();

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
    public List<PakPasandDetailModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<PakPasandDetailModel> detail) {
        this.detail = detail;
    }

}
