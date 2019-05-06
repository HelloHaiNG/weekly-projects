package com.ucarweekly.weeklycommonapi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liaohong
 * @since 2018/11/23 13:54
 */
public class WeeklyReportDto implements Serializable {

    private static final long serialVersionUID = -5341567522150830914L;
    private Integer weeklyId;

    private String weeklyName;

    private Date creatTime;

    private String modifyUser;

    private Date modifyTime;

    private Integer status;

    private Integer downloadCount;

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeeklyId() {
        return weeklyId;
    }

    public void setWeeklyId(Integer weeklyId) {
        this.weeklyId = weeklyId;
    }

    public String getWeeklyName() {
        return weeklyName;
    }

    public void setWeeklyName(String weeklyName) {
        this.weeklyName = weeklyName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}


