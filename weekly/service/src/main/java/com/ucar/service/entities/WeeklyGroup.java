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
 * @since 2018/12/18 10:38
 */
@Table(name = "t_weeklyGroup")
public class WeeklyGroup {

    /**
     * 组别id
     */
    @Id
    @Column(name = "groupId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    /**
     * 组别名字
     */
    @Column(name = "groupName")
    private String groupName;

    @Column(name = "modifyId")
    private Integer modifyId;

    @Column(name = "modifyUser")
    private String modifyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modifyTime")
    private Date modifyTime;

    /**
     * 禁用状态，0表示禁用，1表示启用
     */
    private Integer status;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
