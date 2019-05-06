package com.ucarweekly.usercenterservice.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liaohong
 * @since 2018/11/23 10:00
 */
@Table(name = "t_frontUser")
public class FrontUser {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    /**
     * 0表示停用，1表示正常
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 组别，0表示未设置，其他表示已经设置
     */
    @Column(name = "groupId")
    private Integer groupId;

    /**
     * 0表示应届生，1表示普通人员
     */
    @Column(name = "kindOf")
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
