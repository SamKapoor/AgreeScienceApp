
package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SamacharModel {

    private int result;
    private List<Detail> detail = new ArrayList<Detail>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
