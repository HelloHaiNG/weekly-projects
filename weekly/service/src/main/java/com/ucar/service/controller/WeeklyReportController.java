package com.ucar.service.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucar.api.dto.UserToken;
import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.vo.PageDetail;
import com.ucar.api.vo.RestFulVO;
import com.ucar.service.base.BaseRest;
import com.ucar.service.constants.CommonConstants;
import com.ucar.service.entities.WeeklyReport;
import com.ucar.service.service.WeeklyReportService;
import com.ucar.service.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 13:49
 */
@RestController
public class WeeklyReportController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyReportService weeklyReportService;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date = null;

    /**
     * 添加周报基本信息
     *
     * @param weeklyName
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/weeklyReport/addWeeklyReport")
    public RestFulVO addWeeklyReport(@RequestParam(value = "weeklyName") String weeklyName, @RequestParam(value = "token") String token) throws Exception {
        if (weeklyName == null || token == null) {
            logger.error("添加周报基本信息前端页面参数传参出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        UserToken userToken = JwtUtils.getInfoFromToken(token);
        String userName = userToken.getUserName();
        date = new Date();
        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setWeeklyName(weeklyName);
        weeklyReport.setCreatTime(date);
        weeklyReport.setModifyTime(date);
        weeklyReport.setModifyUser(userName);
        weeklyReport.setDownloadCount(0);
        weeklyReport.setStatus(CommonConstants.STATUS_CLOSE);
        boolean result = weeklyReportService.addWeeklyReport(weeklyReport);
        if (result) {
            logger.info("添加周报基本信息成功");
            return new RestFulVO(WeeklyRetEnum.SUCCESS);
        } else {
            return new RestFulVO(WeeklyRetEnum.ADD_WEEKLYREPORT_ERROR);
        }
    }

    /**
     * 判断周报是否可以编辑
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/enableEditor")
    public RestFulVO enableEditor(@RequestParam(value = "weeklyId") Integer weeklyId) {
        if (weeklyId == null) {
            logger.error("判断当前周报是否可编辑前端页面参数传参出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        return weeklyReportService.enableEditor(weeklyId);
    }

    /**
     * 分页显示基本周报信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/weeklyReport/pageList")
    public RestFulVO pageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            logger.error("分页显示周报基本信息前端页面传参出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        } else {
            Example example = new Example(WeeklyReport.class);
            example.orderBy("creatTime").desc();
            logger.info("当前页码：{}", pageNum);
            PageDetail<WeeklyReportDto> detail = weeklyReportService.pageList(pageNum, pageSize, example);
            return restSuccess(detail);
        }
    }

    /**
     * 修改周报的编辑状态
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/updateStatus")
    public RestFulVO updateStatus(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "status") Integer status, @RequestParam(value = "token") String token) {
        if (weeklyId == null || status == null || token == null) {
            logger.error("修改周报状态时参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        } else {
            Date date = new Date();
            UserToken userToken = null;
            try {
                userToken = JwtUtils.getInfoFromToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userToken == null) {
                logger.error("用户token失效，{}", token);
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            WeeklyReport weeklyReport = new WeeklyReport();
            weeklyReport.setWeeklyId(weeklyId);
            weeklyReport = weeklyReportService.selectById(weeklyReport);
            if (weeklyReport != null) {
                weeklyReport.setModifyTime(date);
                weeklyReport.setModifyUser(userToken.getUserName());
                weeklyReport.setStatus(status);
                weeklyReportService.updateById(weeklyReport);
                return new RestFulVO(WeeklyRetEnum.SUCCESS);
            } else {
                logger.error("周报不存在，{}", weeklyId);
                return new RestFulVO(WeeklyRetEnum.WEEKLYREPORT_NOT_EXIST);
            }
        }
    }

    /**
     * 根据id查找周报基本信息
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/findById")
    public RestFulVO findById(@RequestParam(value = "weeklyId") Integer weeklyId) {
        if (weeklyId == null) {
            logger.info("根据id查找周报信息时前端页面传参出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setWeeklyId(weeklyId);
        weeklyReport = weeklyReportService.selectById(weeklyReport);
        if (weeklyReport == null) {
            logger.info("该id对于的周报信息不存在,{}", weeklyId);
            return new RestFulVO(WeeklyRetEnum.WEEKLYREPORT_NOT_EXIST);
        }
        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        BeanUtils.copyProperties(weeklyReport, weeklyReportDto);
        return restSuccess(weeklyReportDto);
    }

    /**
     * 更新周报下载数
     *
     * @param weeklyId
     * @return
     */
    @PostMapping("/weeklyReport/updateDownloadCount")
    public RestFulVO updateDownloadCount(@RequestParam(value = "weeklyId") Integer weeklyId) {
        if (weeklyId == null) {
            logger.info("更新周报下载数页面参数为null");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        WeeklyReport report = new WeeklyReport();
        report.setWeeklyId(weeklyId);
        report = weeklyReportService.selectById(report);
        if (report == null) {
            return new RestFulVO(WeeklyRetEnum.WEEKLYREPORT_NOT_EXIST);
        } else {
            report.setDownloadCount(report.getDownloadCount() + 1);
            return updateWeeklyReport(report);
        }
    }

    /**
     * 更新周报信息
     *
     * @param weeklyReport
     * @return
     */
    @PostMapping("/weeklyReport/update")
    public RestFulVO updateWeeklyReport(WeeklyReport weeklyReport) {
        if (weeklyReport != null) {
            weeklyReportService.updateById(weeklyReport);
            return restSuccess(weeklyReport);
        } else {
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
    }

    /**
     * 根据周报名称查找周报信息
     *
     * @param pageNum
     * @param pageSize
     * @param weeklyName
     * @return
     */
    @PostMapping("/weeklyReport/findWeeklyByName")
    public RestFulVO findWeeklyByName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize, @RequestParam(value = "weeklyName") String weeklyName) {
        if (weeklyName == null) {
            logger.error("根据周报名称查找周报信息参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        weeklyName = "%".concat(weeklyName).concat("%");
        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setWeeklyName(weeklyName);
        Example example = new Example(WeeklyReport.class);
        example.orderBy("creatTime").desc();
        example.createCriteria().andLike("weeklyName", weeklyName);
        logger.info("当前页码：{}", pageNum);
        PageDetail<WeeklyReportDto> detail = weeklyReportService.pageList(pageNum, pageSize, example);
        return restSuccess(detail);
    }


}
