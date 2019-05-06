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
 * @since 2018/11/23 10:14
 */
@Table(name = "t_weeklyDetail")
public class WeeklyDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weeklyId")
    private Integer weeklyId;

    @Column(name = "weeklyName")
    private String weeklyName;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "editorTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;

    @Column(name = "groupId")
    private Integer groupId;

    @Column(name = "thisWeekContent")
    private String thisWeekContent;

    @Column(name = "nextWeekContent")
    private String nextWeekContent;

    @Column(name = "trouble")
    private String trouble;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public String getThisWeekContent() {
        return thisWeekContent;
    }

    public void setThisWeekContent(String thisWeekContent) {
        this.thisWeekContent = thisWeekContent;
    }

    public String getNextWeekContent() {
        return nextWeekContent;
    }

    public void setNextWeekContent(String nextWeekContent) {
        this.nextWeekContent = nextWeekContent;
    }

    public String getTrouble() {
        return trouble;
    }

    public void setTrouble(String trouble) {
        this.trouble = trouble;
    }
}
