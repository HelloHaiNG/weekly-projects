package com.ucar.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ucar.api.dto.WeeklyDetailDto;
import com.ucar.api.vo.PageDetail;
import com.ucar.service.entities.WeeklyDetail;
import com.ucar.service.mapper.WeeklyDetailMapper;
import com.ucar.service.service.WeeklyDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 17:01
 */
@Service
public class WeeklyDetailServiceImpl implements WeeklyDetailService {

    @Autowired
    private WeeklyDetailMapper weeklyDetailMapper;

    @Override
    public boolean add(WeeklyDetail weeklyDetail) {
       return weeklyDetailMapper.insert(weeklyDetail) != 0;
    }

    @Override
    public List<WeeklyDetail> details(Integer weeklyId, Integer userId) {
        Example example = new Example(WeeklyDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("weeklyId", weeklyId);
        criteria.andEqualTo("userId", userId);
        return weeklyDetailMapper.selectByExample(example);
    }

    @Override
    public PageDetail<WeeklyDetailDto> pageList(Integer pageNum, Integer pageSize, Example example) {
        PageHelper.startPage(pageNum,pageSize);
        List<WeeklyDetail> details = weeklyDetailMapper.selectByExample(example);
        List<WeeklyDetailDto> detailDtos = new ArrayList<>();
        for (WeeklyDetail detail: details) {
            WeeklyDetailDto detailDto = new WeeklyDetailDto();
            BeanUtils.copyProperties(detail,detailDto);
            detailDtos.add(detailDto);
        }
        Integer count = count(example);
        return new PageDetail<>(detailDtos,pageNum,pageSize,count);
    }

    @Override
    public Integer count(Example example) {
        return weeklyDetailMapper.selectCountByExample(example);
    }

    @Override
    public WeeklyDetail findById(WeeklyDetail weeklyDetail) {
        return weeklyDetailMapper.selectByPrimaryKey(weeklyDetail);
    }

    @Override
    public List<WeeklyDetail> findByWeeklyId(String weeklyId) {
        Example example = new Example(WeeklyDetail.class);
        example.createCriteria().andEqualTo("weeklyId", weeklyId);
        return weeklyDetailMapper.selectByExample(example);
    }
}
