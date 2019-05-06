package com.ucar.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ucar.api.dto.WeeklyGroupDto;
import com.ucar.api.vo.PageDetail;
import com.ucar.service.entities.WeeklyGroup;
import com.ucar.service.mapper.WeeklyGroupMapper;
import com.ucar.service.service.WeeklyGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/12/18 11:55
 */
@Service
public class WeeklyGroupServiceImpl implements WeeklyGroupService {
    @Autowired
    private WeeklyGroupMapper weeklyGroupMapper;

    @Override
    public void add(WeeklyGroup weeklyGroup) {
        weeklyGroupMapper.insert(weeklyGroup);
    }

    @Override
    public List<WeeklyGroup> allGroup() {
        return weeklyGroupMapper.selectAll();
    }

    @Override
    public WeeklyGroup selectById(WeeklyGroup weeklyGroup) {
        return weeklyGroupMapper.selectByPrimaryKey(weeklyGroup);
    }

    @Override
    public void update(WeeklyGroup weeklyGroup) {
        weeklyGroupMapper.updateByPrimaryKey(weeklyGroup);
    }

    @Override
    public List<WeeklyGroup> findByName(String groupName) {
        Example example = new Example(WeeklyGroup.class);
        example.createCriteria().andLike("groupName", groupName);
        return weeklyGroupMapper.selectByExample(example);
    }

    @Override
    public PageDetail<WeeklyGroupDto> pageList(Integer pageNum, Integer pageSize) {
        Example example = new Example(WeeklyGroup.class);
        example.orderBy("modifyTime").desc();
        PageHelper.startPage(pageNum, pageSize);
        List<WeeklyGroup> groups = weeklyGroupMapper.selectByExample(example);
        List<WeeklyGroupDto> groupDtos = new ArrayList<>();
        for (WeeklyGroup weeklyGroup : groups) {
            WeeklyGroupDto dto = new WeeklyGroupDto();
            BeanUtils.copyProperties(weeklyGroup, dto);
            groupDtos.add(dto);
        }
        Integer count = groupCount();
        return new PageDetail<>(groupDtos, pageNum, pageSize, count);
    }

    @Override
    public Integer groupCount() {
        return weeklyGroupMapper.selectAll().size();
    }

    @Override
    public PageDetail<WeeklyGroupDto> pageListByGroupName(Integer pageNum, Integer pageSize, String groupName) {
        Example example = new Example(WeeklyGroup.class);
        groupName = "%".concat(groupName.concat("%"));
        example.createCriteria().andLike("groupName", groupName);
        example.orderBy("modifyTime").desc();
        PageHelper.startPage(pageNum, pageSize);
        List<WeeklyGroup> groups = weeklyGroupMapper.selectByExample(example);
        List<WeeklyGroupDto> groupDtos = new ArrayList<>();
        for (WeeklyGroup weeklyGroup : groups) {
            WeeklyGroupDto dto = new WeeklyGroupDto();
            BeanUtils.copyProperties(weeklyGroup, dto);
            groupDtos.add(dto);
        }
        Integer count = groupCountByGroupName(groupName);
        return new PageDetail<>(groupDtos, pageNum, pageSize, count);
    }

    @Override
    public Integer groupCountByGroupName(String groupName) {
        Example example = new Example(WeeklyGroup.class);
        example.createCriteria().andLike("groupName", groupName);
        return weeklyGroupMapper.selectByExample(example).size();
    }
}
