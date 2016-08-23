package com.agriscienceapp.model;

/**
 * Created by Kanak on 1/14/2016.
 */
public class SamacharDetailDescModel {

    private int NewsId;
    private String NewsTitle;
    private String NewsTimeline;
    private String NewsDetail;
    private String NewsDate;
    private String Status;
    private String CreatedBy;
    private String CreatedDate;
    private String UpdatedBy;
    private String UpdatedDate;
    private String DetailAdd;
    private String IsExist;
    private int Priority;
    private String Photo;

    public int getNewsId() {
        return NewsId;
    }

    public void setNewsId(int newsId) {
        NewsId = newsId;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public String getNewsTimeline() {
        return NewsTimeline;
    }

    public void setNewsTimeline(String newsTimeline) {
        NewsTimeline = newsTimeline;
    }


    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDetail() {
        return NewsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        NewsDetail = newsDetail;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String newsDate) {
        NewsDate = newsDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDetailAdd() {
        return DetailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        DetailAdd = detailAdd;
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

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
