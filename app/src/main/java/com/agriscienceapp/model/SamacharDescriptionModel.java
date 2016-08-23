
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SamacharDescriptionModel {

    private Integer result;

    @SerializedName("detail")
    private List<SamacharDetailDescModel> detail = new ArrayList<SamacharDetailDescModel>();


    /**
     *
     * @return
     *     The result
     */

    public Integer getResult() {
        return result;
    }

    /**
     *
     * @param result
     *     The result
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     *
     * @return
     *     The detail
     */
    public List<SamacharDetailDescModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<SamacharDetailDescModel> detail) {
        this.detail = detail;
    }




}
