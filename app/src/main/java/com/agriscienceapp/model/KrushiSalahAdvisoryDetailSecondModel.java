
package com.agriscienceapp.model;

import java.util.HashMap;
import java.util.Map;

public class KrushiSalahAdvisoryDetailSecondModel {

    private int AdviseId;
    private String Photo;
    private String Thumbs;
    private String DetailAdd;
    private String ContactNo;
    private String Popup;

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getPopup() {
        return Popup;
    }

    public void setPopup(String popup) {
        Popup = popup;
    }

    public String getDetailAdd() {
        return DetailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        DetailAdd = detailAdd;
    }


    private String AdviseTitle;
    private String AdviseFilePath;
    private String AdviseDate;
    private String Status;
    private String CreatedBy;
    private String CreatedDate;
    private String UpdatedBy;
    private String UpdatedDate;
    private String IsExist;
    private String Description;
    private String Extension;
    private int CropId;
    private int CropTitleId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getAdviseId() {
        return AdviseId;
    }

    public void setAdviseId(int adviseId) {
        AdviseId = adviseId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThumbs() {
        return Thumbs;
    }

    public void setThumbs(String thumbs) {
        Thumbs = thumbs;
    }

    public String getAdviseTitle() {
        return AdviseTitle;
    }

    public void setAdviseTitle(String adviseTitle) {
        AdviseTitle = adviseTitle;
    }

    public String getAdviseFilePath() {
        return AdviseFilePath;
    }

    public void setAdviseFilePath(String adviseFilePath) {
        AdviseFilePath = adviseFilePath;
    }

    public String getAdviseDate() {
        return AdviseDate;
    }

    public void setAdviseDate(String adviseDate) {
        AdviseDate = adviseDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getIsExist() {
        return IsExist;
    }

    public void setIsExist(String isExist) {
        IsExist = isExist;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public int getCropId() {
        return CropId;
    }

    public void setCropId(int cropId) {
        CropId = cropId;
    }

    public int getCropTitleId() {
        return CropTitleId;
    }

    public void setCropTitleId(int cropTitleId) {
        CropTitleId = cropTitleId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
