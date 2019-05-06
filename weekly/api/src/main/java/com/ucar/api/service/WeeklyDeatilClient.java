package com.ucar.api.service;

import com.ucar.api.WeeklyConst;
import com.ucar.api.hystrix.WeeklyDetailClientFallbackFactory;
import com.ucar.api.vo.RestFulVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/26 13:55
 */
@FeignClient(value = WeeklyConst.Weekly_SERVICE_NAME, fallbackFactory = WeeklyDetailClientFallbackFactory.class)
public interface WeeklyDeatilClient {

    /**
     * 根据userId查找周报详情
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/detail/pageListByUserId")
    RestFulVO pageListByUserId(@RequestParam(value = "token") String token, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize);

    /**
     * 查找个人某一期的周报内容
     *
     * @param weeklyId
     * @param token
     * @return
     */
    @PostMapping("/detail/weeklyDetailByUser")
    RestFulVO weeklyDetailByUser(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "token") String token);

    /**
     * 根据id批量查找周报详情信息
     * @param ids
     * @return
     */
    @PostMapping("/deatail/findByIds")
    RestFulVO findById(@RequestParam(value = "ids[]") String[] ids);

    /**
     * 根据weeklyid查询周报列表
     * @param weeklyId
     * @return
     */
    @PostMapping("/detail/findByWeeklyId")
    RestFulVO findByWeeklyId(@RequestParam(value = "weeklyIds") String weeklyId);
}
