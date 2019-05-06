package com.ucar.service.service;

import com.ucar.api.dto.WeeklyGroupDto;
import com.ucar.api.vo.PageDetail;
import com.ucar.service.entities.WeeklyGroup;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/12/18 11:54
 */
public interface WeeklyGroupService {

    void add(WeeklyGroup weeklyGroup);

    List<WeeklyGroup> allGroup();

    WeeklyGroup selectById(WeeklyGroup weeklyGroup);

    void update(WeeklyGroup weeklyGroup);

    List<WeeklyGroup> findByName(String groupName);

    PageDetail<WeeklyGroupDto> pageList(Integer pageNum, Integer pageSize);

    Integer groupCount();

    PageDetail<WeeklyGroupDto> pageListByGroupName(Integer pageNum, Integer pageSize, String groupName);

    Integer groupCountByGroupName(String groupName);
}
