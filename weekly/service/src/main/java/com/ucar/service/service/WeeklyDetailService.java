package com.ucar.service.service;

import com.ucar.api.dto.WeeklyDetailDto;
import com.ucar.api.vo.PageDetail;
import com.ucar.service.entities.WeeklyDetail;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 17:00
 */
public interface WeeklyDetailService {

    boolean add(WeeklyDetail weeklyDetail);

    List<WeeklyDetail> details(Integer weeklyId,Integer userId);

    PageDetail<WeeklyDetailDto> pageList(Integer pageNum,Integer pageSize,Example example);

    Integer count(Example example);

    WeeklyDetail findById (WeeklyDetail weeklyDetail);

    List<WeeklyDetail> findByWeeklyId(String weeklyId);
}
