package com.ucarweekly.usercenterapi;

/**
 * ClassName BBSPostConst.java
 * Description API中常量
 *
 * @author wanglang
 * date 2018/9/10  15:04
 * @version 1.0
 **/
public final class WeeklyUserCenterConst {

    /**
     * 服务名称
     */
    public static final String WeeklyUserCenter_SERVICE_NAME = "weeklyusercenter-service";

    /**
     * feign client 包扫描路径
     */
    public static final String WeeklyUserCenter_CLIENT_SCAN_PATH = "com.ucarweekly.usercenterapi.service";

    /**
     * feign client 熔断器扫描路径
     */
    public static final String WeeklyUserCenter_CLIENT_HYSTRIX_SCAN_PATH = "com.ucarweekly.usercenterapi.hystrix";

}
