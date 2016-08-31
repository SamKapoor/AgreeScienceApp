
package com.agriscienceapp.model;

import java.util.HashMap;
import java.util.Map;

public class Detail {

    private int NewsId;
    private String NewsTitle;
    private String NewsDetail;
    private String NewsDate;
    private String Thumbs;
    private String Photo;
    private String Status;
    private int CreatedBy;
    private String CreatedDate;
    private int UpdatedBy;
    private String UpdatedDate;
    private String MainAdd;
    private String ContactNo;
    private String Popup;

    private int IsExist;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    /**
     * 
     * @return
     *     The NewsId
     */
    public int getNewsId() {
        return NewsId;
    }

    /**
     * 
     * @param NewsId
     *     The NewsId
     */
    public void setNewsId(int NewsId) {
        this.NewsId = NewsId;
    }

    /**
     * 
     * @return
     *     The NewsTitle
     */
    public String getNewsTitle() {
        return NewsTitle;
    }

    /**
     * 
     * @param NewsTitle
     *     The NewsTitle
     */
    public void setNewsTitle(String NewsTitle) {
        this.NewsTitle = NewsTitle;
    }

    /**
     * 
     * @return
     *     The NewsDetail
     */
    public String getNewsDetail() {
        return NewsDetail;
    }

    /**
     * 
     * @param NewsDetail
     *     The NewsDetail
     */
    public void setNewsDetail(String NewsDetail) {
        this.NewsDetail = NewsDetail;
    }

    /**
     * 
     * @return
     *     The NewsDate
     */
    public String getNewsDate() {
        return NewsDate;
    }

    /**
     * 
     * @param NewsDate
     *     The NewsDate
     */
    public void setNewsDate(String NewsDate) {
        this.NewsDate = NewsDate;
    }

    /**
     * 
     * @return
     *     The Thumbs
     */
    public String getThumbs() {
        return Thumbs;
    }

    /**
     * 
     * @param Thumbs
     *     The Thumbs
     */
    public void setThumbs(String Thumbs) {
        this.Thumbs = Thumbs;
    }

    /**
     * 
     * @return
     *     The Photo
     */
    public String getPhoto() {
        return Photo;
    }

    /**
     * 
     * @param Photo
     *     The Photo
     */
    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    /**
     * 
     * @return
     *     The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * 
     * @param Status
     *     The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * 
     * @return
     *     The CreatedBy
     */
    public int getCreatedBy() {
        return CreatedBy;
    }

    /**
     * 
     * @param CreatedBy
     *     The CreatedBy
     */
    public void setCreatedBy(int CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    /**
     * 
     * @return
     *     The CreatedDate
     */
    public String getCreatedDate() {
        return CreatedDate;
    }

    /**
     * 
     * @param CreatedDate
     *     The CreatedDate
     */
    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    /**
     * 
     * @return
     *     The UpdatedBy
     */
    public int getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * 
     * @param UpdatedBy
     *     The UpdatedBy
     */
    public void setUpdatedBy(int UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

    /**
     * 
     * @return
     *     The UpdatedDate
     */
    public String getUpdatedDate() {
        return UpdatedDate;
    }

    /**
     * 
     * @param UpdatedDate
     *     The UpdatedDate
     */
    public void setUpdatedDate(String UpdatedDate) {
        this.UpdatedDate = UpdatedDate;
    }

    /**
     * 
     * @return
     *     The IsExist
     */

    public String getMainAdd() {
        return MainAdd;
    }

    public void setMainAdd(String mainAdd) {
        MainAdd = mainAdd;
    }
    public int getIsExist() {
        return IsExist;
    }

    /**
     * 
     * @param IsExist
     *     The IsExist
     */
    public void setIsExist(int IsExist) {
        this.IsExist = IsExist;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
