package com.ucar.api.service;

import com.ucar.api.WeeklyConst;
import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.hystrix.WeeklyReportClientFallbackFactory;
import com.ucar.api.vo.RestFulVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/26 10:25
 */
@FeignClient(value = WeeklyConst.Weekly_SERVICE_NAME, fallbackFactory = WeeklyReportClientFallbackFactory.class)
public interface WeeklyReportClient {

    /**
     * 修改周报的编辑状态
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/updateStatus")
    RestFulVO updateStatus(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "status") Integer status, @RequestParam(value = "token") String token);

    /**
     * 添加周报基本信息
     *
     * @param weeklyName
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/weeklyReport/addWeeklyReport")
    RestFulVO addWeeklyReport(@RequestParam(value = "weeklyName") String weeklyName, @RequestParam(value = "token") String token) throws Exception;

    /**
     * 分页显示基本周报信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/weeklyReport/pageList")
    RestFulVO pageList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "13") Integer pageSize);

    /**
     * 根据周报id查找周报基本信息
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/findById")
    RestFulVO findById(@RequestParam(value = "weeklyId") Integer weeklyId);

    /**
     * 更新周报下载数
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/updateDownloadCount")
    RestFulVO updateDownloadCount(@RequestParam(value = "weeklyId") Integer weeklyId);

    /**
     * 根据周报名称查找周报信息
     *
     * @param pageNum
     * @param pageSize
     * @param weeklyName
     * @return
     */
    @PostMapping("/weeklyReport/findWeeklyByName")
    RestFulVO findWeeklyByName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize, @RequestParam(value = "weeklyName") String weeklyName);
}
