package com.ucarweekly.weeklycommonapi.service;

import com.ucarweekly.weeklycommonapi.WeeklyCommonConst;
import com.ucarweekly.weeklycommonapi.hystrix.DownLoadClientFallbackFactory;
import com.ucarweekly.weeklycommonapi.vo.RestFulVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/28 10:59
 */
@FeignClient(value = WeeklyCommonConst.WeeklyCommon_SERVICE_NAME, fallbackFactory = DownLoadClientFallbackFactory.class)
public interface DownLoadClient {

    /**
     * 下载周报
     *
     * @param weeklyId
     * @return
     * @throws Exception
     */
    @PostMapping("/download/weeklyReport")
    RestFulVO weeklyReport(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "token") String token) throws Exception;
}
