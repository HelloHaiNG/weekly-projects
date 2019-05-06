package com.ucar.weeklyadminwebapi.dto;

import java.io.Serializable;

/**
 * @author liaohong
 * @since 2018/11/26 10:09
 */
public class BackUserDto implements Serializable {

    private static final long serialVersionUID = 8162097748331028179L;
    private Integer userId;

    private String userName;

    private String password;

    /**
     * 0表示停用，1表示启用
     */
    private Integer status;

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
}
