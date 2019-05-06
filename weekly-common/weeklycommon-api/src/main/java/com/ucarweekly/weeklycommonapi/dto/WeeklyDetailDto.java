package com.ucarweekly.weeklycommonapi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liaohong
 * @since 2018/11/23 17:07
 */
public class WeeklyDetailDto implements Serializable {

    private static final long serialVersionUID = 789800862017954807L;
    private Integer id;

    private Integer weeklyId;

    private String weeklyName;

    private Integer userId;

    private String userName;

    private Integer groupId;

    private Date editorTime;

    private String thisWeekContent;

    private String nextWeekContent;

    private String trouble;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
