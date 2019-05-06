package com.ucarweekly.usercenterapi.dto;

import java.io.Serializable;

/**
 * @author liaohong
 * @since 2018/11/23 10:36
 */
public class FrontUserDto implements Serializable {

    private static final long serialVersionUID = 7102814711668371069L;
    private Integer userId;
    private String userName;
    private String password;
    private Integer groupId;

    /**
     * 0表示停用，1表示正常
     */
    private Integer status;

    /**
     * 0表示应届生，1表示普通人员
     */
    private Integer kindOf;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getKindOf() {
        return kindOf;
    }

    public void setKindOf(Integer kindOf) {
        this.kindOf = kindOf;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
