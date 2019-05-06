package com.ucar.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.vo.PageDetail;
import com.ucar.api.vo.RestFulVO;
import com.ucar.service.constants.CommonConstants;
import com.ucar.service.entities.WeeklyReport;
import com.ucar.service.mapper.WeeklyReportMapper;
import com.ucar.service.service.WeeklyReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 13:59
 */
@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyReportMapper weeklyReportMapper;

    @Override
    public boolean addWeeklyReport(WeeklyReport weeklyReport) {
        return weeklyReportMapper.insert(weeklyReport) != 0;
    }

    @Override
    public PageDetail<WeeklyReportDto> pageList(Integer pageNum, Integer pageSize, Example example) {
        PageHelper.startPage(pageNum, pageSize);
        List<WeeklyReport> list = weeklyReportMapper.selectByExample(example);
        List<WeeklyReportDto> dtoList = new ArrayList<>();
        for (WeeklyReport weeklyReport : list) {
            WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
            BeanUtils.copyProperties(weeklyReport, weeklyReportDto);
            dtoList.add(weeklyReportDto);
        }
        Integer total = this.weeklyReportCount(example);
        return new PageDetail<>(dtoList, pageNum, pageSize, total);
    }

    @Override
    public Integer weeklyReportCount(Example example) {
        return weeklyReportMapper.selectCountByExample(example);
    }

    @Override
    public WeeklyReport selectById(WeeklyReport weeklyReport) {
        return weeklyReportMapper.selectByPrimaryKey(weeklyReport);
    }

    @Override
    public void updateById(WeeklyReport weeklyReport) {
        weeklyReportMapper.updateByPrimaryKey(weeklyReport);
    }

    @Override
    public RestFulVO enableEditor(Integer weeklyId) {
        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setWeeklyId(weeklyId);
        WeeklyReport report = selectById(weeklyReport);
        if (report == null) {
            logger.info("该周报不存在,{}", weeklyId);
            return new RestFulVO(WeeklyRetEnum.WEEKLYREPORT_NOT_EXIST);
        }
        Integer status = report.getStatus();
        if (status == CommonConstants.STATUS_CLOSE) {
            logger.info("当前周报不可被编辑,{}", weeklyId);
            return new RestFulVO(WeeklyRetEnum.WEEKLYREPORT_STATUS_CLOSED);
        }
        return new RestFulVO(WeeklyRetEnum.SUCCESS);
    }
}


