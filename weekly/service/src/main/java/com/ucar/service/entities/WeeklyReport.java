package com.ucar.service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liaohong
 * @since 2018/11/23 9:49
 */
@Table(name = "t_weekly")
public class WeeklyReport {

    @Id
    @Column(name = "weeklyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer weeklyId;

    @Column(name = "weeklyName")
    private String weeklyName;

    @Column(name = "creatTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creatTime;

    @Column(name = "modifyUser")
    private String modifyUser;

    @Column(name = "modifyTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "downloadCount")
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
