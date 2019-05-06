package com.ucarweekly.weeklycommonapi;

/**
 * ClassName BBSPostConst.java
 * Description API中常量
 *
 * @author wanglang
 * date 2018/9/10  15:04
 * @version 1.0
 **/
public final class WeeklyCommonConst {

    /**
     * 服务名称
     */
    public static final String WeeklyCommon_SERVICE_NAME = "weeklycommon-service";

    /**
     * feign client 包扫描路径
     */
    public static final String WeeklyCommon_CLIENT_SCAN_PATH = "com.ucarweekly.weeklycommonapi.service";

    /**
     * feign client 熔断器扫描路径
     */
    public static final String WeeklyCommon_CLIENT_HYSTRIX_SCAN_PATH = "com.ucarweekly.weeklycommonapi.hystrix";

}
