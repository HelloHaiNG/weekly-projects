package com.ucarweekly.weeklycommonservice.controller;

import com.ucarweekly.weeklycommonapi.msg.WeeklyCommonRetEnum;
import com.ucarweekly.weeklycommonapi.vo.RestFulVO;
import com.ucarweekly.weeklycommonservice.service.DownLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohong
 * @since 2018/11/27 9:15
 */
@RestController
public class DownLoadController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DownLoadService downLoadService;

    /**
     * 下载周报
     *
     * @param weeklyId
     * @return
     * @throws Exception
     */
    @PostMapping("/download/weeklyReport")
    public RestFulVO weeklyReport(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "token") String token) throws Exception {
        if (weeklyId == null || token == null) {
            logger.info("下载周报内容前端页面传参出错");
            return new RestFulVO(WeeklyCommonRetEnum.PARAM_ERROR);
        }
        return downLoadService.downloadWeeklyReport(weeklyId, token);
    }

    /**
     * 下载个人周报
     *
     * @param ids
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/download/myWeeklys")
    public RestFulVO myWeeklys(@RequestParam(value = "ids") String ids, @RequestParam(value = "token") String token) throws Exception {
        if (ids == null || token == null) {
            logger.info("下载个人周报内容前端页面传参出错");
            return new RestFulVO(WeeklyCommonRetEnum.PARAM_ERROR);
        }
        String[] split = ids.split("-");
        return downLoadService.downloadMyWeeklys(split, token);
    }

    /**
     * 根据weeklyId下载周报列表信息
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/download/byWeeklyId")
    public RestFulVO byWeeklyId(@RequestParam(value = "weeklyId") String weeklyId) throws Exception {
        if (weeklyId == null) {
            logger.info("根据weeklyid下载周报内容前端页面传参出错");
            return new RestFulVO(WeeklyCommonRetEnum.PARAM_ERROR);
        }
        return downLoadService.downloadByWeeklyId(weeklyId);
    }


}
