package com.agriscienceapp.model;

/**
 * Created by Administrator on 3/22/2016.
 */
public class TermsAndConditons {

    int TermsId;
    String TermsCondition;
    String TermsStatus;
    String IsExist;

    public int getTermsId() {
        return TermsId;
    }

    public void setTermsId(int termsId) {
        TermsId = termsId;
    }

    public TermsAndConditons(int termsId, String termsCondition, String termsStatus, String isExist) {
        TermsId = termsId;
        TermsCondition = termsCondition;
        TermsStatus = termsStatus;
        IsExist = isExist;
    }

    public String getTermsCondition() {

        return TermsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        TermsCondition = termsCondition;
    }

    public String getTermsStatus() {
        return TermsStatus;
    }

    public void setTermsStatus(String termsStatus) {
        TermsStatus = termsStatus;
    }

    public String getIsExist() {
        return IsExist;
    }

    public void setIsExist(String isExist) {
        IsExist = isExist;
    }

    public TermsAndConditons() {

    }

}