package com.ucar.api.msg;


import org.apache.commons.lang3.StringUtils;

/**
 * ClassName WeeklyRetEnum.java
 * Description 返回信息枚举类
 *
 * @author wanglang
 * date 2018/9/10  16:32
 * @version 1.0
 **/
public enum WeeklyRetEnum {

    SUCCESS("0000", "操作成功"),

    PARAM_ERROR("1001", "页面传参出错"),

    LOGIN_ERROR("1002", "登陆失败"),

    ADD_WEEKLYREPORT_ERROR("1003", "添加周报基本信息失败"),

    WEEKLYREPORT_NOT_EXIST("1004", "周报不存在"),

    WEEKLYREPORT_STATUS_CLOSED("1005", "周报暂不可编辑"),

    WEEKLYDETAIL_EDITOR_ERROR("1006", "周报书写失败"),

    WEEKLY_NOT_YET_WRITE("1007", "您还未写该周报"),

    WEEKLY_HAS_WRITE("1007", "您已经写了该周报"),

    TOEKN_INVALID("1008", "token失效"),

    USER_NOT_GROUP("1009", "用户没有指定组别"),

    GROUP_NOT_EXIST("1010", "组别不存在"),

    ERROR("1111", "操作失败");

    private String code;
    private String msg;

    WeeklyRetEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code) {
        for (WeeklyRetEnum systemException : WeeklyRetEnum.values()) {
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
