package com.ucar.weeklyadminwebapi.msg;


import org.apache.commons.lang3.StringUtils;

/**
 * ClassName WeeklyAdminWebRetEnum.java
 * Description 返回信息枚举类
 *
 * @author wanglang
 * date 2018/9/10  16:32
 * @version 1.0
 **/
public enum WeeklyAdminWebRetEnum {

    SUCCESS("0000", "操作成功"),

    PARAM_ERROR("1001", "页面传参出错"),

    LOGIN_ERROR("1002", "登陆失败"),

    ADD_WEEKLYREPORT_ERROR("1003", "添加周报基本信息失败"),

    WEEKLYREPORT_NOT_EXIST("1004", "周报不存在"),

    WEEKLYREPORT_STATUS_CLOSED("1005", "周报暂不可编辑"),

    WEEKLYDETAIL_EDITOR_ERROR("1006", "周报书写失败"),

    TOKEN_INVILD("1007", "token失效"),

    ERROR("1111", "操作失败");

    private String code;
    private String msg;

    WeeklyAdminWebRetEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code) {
        for (WeeklyAdminWebRetEnum systemException : WeeklyAdminWebRetEnum.values()) {
            if (StringUtils.equals(systemException.getCode(), code)) {
                return systemException.getMsg();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
