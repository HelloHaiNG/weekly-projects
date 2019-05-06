package com.ucar.service.service;

import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.vo.PageDetail;
import com.ucar.api.vo.RestFulVO;
import com.ucar.service.entities.WeeklyReport;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 13:59
 */
public interface WeeklyReportService {

    boolean addWeeklyReport(WeeklyReport weeklyReport);

    PageDetail<WeeklyReportDto> pageList(Integer pageNum, Integer pageSize, Example example);

    Integer weeklyReportCount(Example example);

    WeeklyReport selectById(WeeklyReport weeklyReport);

    void updateById(WeeklyReport weeklyReport);

    RestFulVO enableEditor(Integer weeklyId);

}
