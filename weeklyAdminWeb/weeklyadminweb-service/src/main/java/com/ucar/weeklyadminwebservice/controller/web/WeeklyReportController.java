package com.ucar.weeklyadminwebservice.controller.web;

import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.service.WeeklyReportClient;
import com.ucar.weeklyadminwebapi.msg.WeeklyAdminWebRetEnum;
import com.ucar.weeklyadminwebapi.vo.RestFulVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohong
 * @since 2018/11/26 10:43
 */
@RestController
public class WeeklyReportController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyReportClient weeklyReportClient;

    /**
     * 添加周报基本信息
     *
     * @param weeklyName
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/weeklyadminweb/addWeeklyReport")
    public RestFulVO addWeeklyReport(@RequestParam(value = "weeklyName") String weeklyName, @RequestParam(value = "token") String token) throws Exception {
        com.ucar.api.vo.RestFulVO vo = weeklyReportClient.addWeeklyReport(weeklyName, token);
        return new RestFulVO(vo.getRestCode(), vo.getRestMsg());
    }

    /**
     * 修改周报的编辑状态
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyadminweb/updateStatus")
    public RestFulVO updateStatus(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "status") String status, @RequestParam(value = "token") String token) {
        if (weeklyId == null || status == null || token == null) {
            logger.error("修改周报状态时参数传递出错");
            return new RestFulVO(WeeklyAdminWebRetEnum.PARAM_ERROR);
        } else {
            com.ucar.api.vo.RestFulVO vo = weeklyReportClient.updateStatus(weeklyId, Integer.parseInt(status), token);
            return new RestFulVO(vo.getRestCode(), vo.getRestMsg());
        }
    }

    /**
     * 分页显示基本周报信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/weeklyadminweb/pageList")
    public RestFulVO pageList(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            logger.error("分页显示周报基本信息前端页面传参出错");
            return new RestFulVO(WeeklyAdminWebRetEnum.PARAM_ERROR);
        }
        com.ucar.api.vo.RestFulVO vo = weeklyReportClient.pageList(pageNum, pageSize);
        return new RestFulVO(vo.getRestCode(), vo.getRestMsg(), vo.getData());
    }

    /**
     * 根据周报名称查询周报信息
     *
     * @param pageNum
     * @param pageSize
     * @param weeklyName
     * @return
     */
    @PostMapping("/weeklyadminweb/findWeeklyByName")
    public RestFulVO findWeeklyByName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize, @RequestParam(value = "weeklyName") String weeklyName) {
        com.ucar.api.vo.RestFulVO vo = weeklyReportClient.findWeeklyByName(pageNum, pageSize, weeklyName);
        return new RestFulVO(vo.getRestCode(), vo.getRestMsg(), vo.getData());
    }


}
