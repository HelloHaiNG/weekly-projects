package com.ucarweekly.zuul.dto;

import java.io.Serializable;

/**
 * @author liaohong
 * @since 2018/11/23 10:38
 */
public class UserToken implements Serializable {

    private static final long serialVersionUID = -105139176255637487L;
    private Integer userId;
    private String userName;

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
}
