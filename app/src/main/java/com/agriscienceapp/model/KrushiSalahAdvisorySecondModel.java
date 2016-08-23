
package com.agriscienceapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KrushiSalahAdvisorySecondModel {

    private int result;
    private List<KrushiSalahAdvisoryDetailSecondModel> detail = new ArrayList<KrushiSalahAdvisoryDetailSecondModel>();
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
    public List<KrushiSalahAdvisoryDetailSecondModel> getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     *     The detail
     */
    public void setDetail(List<KrushiSalahAdvisoryDetailSecondModel> detail) {
        this.detail = detail;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
