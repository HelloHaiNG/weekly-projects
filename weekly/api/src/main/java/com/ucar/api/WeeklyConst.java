package com.ucar.api;

/**
 * ClassName BBSPostConst.java
 * Description API中常量
 *
 * @author wanglang
 * date 2018/9/10  15:04
 * @version 1.0
 **/
public final class WeeklyConst {

    /**
     * 服务名称
     */
    public static final String Weekly_SERVICE_NAME = "weekly-service";

    /**
     * feign client 包扫描路径
     */
    public static final String Weekly_CLIENT_SCAN_PATH = "com.ucar.api.service";

    /**
     * feign client 熔断器扫描路径
     */
    public static final String Weekly_CLIENT_HYSTRIX_SCAN_PATH = "com.ucar.api.hystrix";

}
