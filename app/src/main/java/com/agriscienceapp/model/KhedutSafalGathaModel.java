
package com.agriscienceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KhedutSafalGathaModel {

    private int result;

    @SerializedName("detail")
    private List<KheduSafalGathaDetailModel> detail = new ArrayList<KheduSafalGathaDetailModel>();

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
    public List<KheduSafalGathaDetailModel> getDetail() {
        return detail;
    }

    /**
     * 
     * @param detail
     *     The detail
     */
    public void setDetail(List<KheduSafalGathaDetailModel> detail) {
        this.detail = detail;
    }

}
