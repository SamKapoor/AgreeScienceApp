package com.agriscienceapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MessageListVo {

    public String Notificationid;
    public String RefId;
    public String NotificationType;
    public String NewsTitle;
//	private boolean isSelf;


    public MessageListVo() {
    }

    public MessageListVo(String notificationid, String refId, String notificationType, String newsTitle) {
        Notificationid = notificationid;
        RefId = refId;
        NotificationType = notificationType;
        NewsTitle = newsTitle;
    }

    public String getNotificationid() {
        return Notificationid;
    }

    public void setNotificationid(String notificationid) {
        Notificationid = notificationid;
    }

    public String getRefId() {
        return RefId;
    }

    public void setRefId(String refId) {
        RefId = refId;
    }

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }


}
