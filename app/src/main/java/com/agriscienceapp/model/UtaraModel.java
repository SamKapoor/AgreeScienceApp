
package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.List;

public class UtaraModel {

//    @SerializedName("detail")
    private int result;
    private List<utaraDetail> detail = new ArrayList<utaraDetail>();

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
    public List<utaraDetail> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<utaraDetail> detail) {
        this.detail = detail;
    }

}
