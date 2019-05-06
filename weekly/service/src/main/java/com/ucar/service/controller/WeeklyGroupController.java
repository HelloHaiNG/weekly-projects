package com.ucar.service.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucar.api.dto.UserToken;
import com.ucar.api.dto.WeeklyGroupDto;
import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.vo.PageDetail;
import com.ucar.api.vo.RestFulVO;
import com.ucar.service.base.BaseRest;
import com.ucar.service.constants.CommonConstants;
import com.ucar.service.entities.WeeklyGroup;
import com.ucar.service.service.WeeklyGroupService;
import com.ucar.service.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/12/18 11:52
 */
@RestController
public class WeeklyGroupController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private WeeklyGroupService groupService;

    /**
     * 添加组别信息
     *
     * @param groupName
     * @param token
     * @return
     * @throws ParseException
     */
    @PostMapping("/group/addGroup")
    public RestFulVO addGroup(@RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token) {
        if (groupName == null || token == null) {
            logger.error("添加组别信息参数为空");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        try {
            Date date = new Date();
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.error("用户token失效，{}", token);
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            String userName = userToken.getUserName();
            Integer userId = userToken.getUserId();
            WeeklyGroup weeklyGroup = new WeeklyGroup();
            groupName = groupName.concat("组");
            weeklyGroup.setGroupName(groupName);
            weeklyGroup.setModifyId(userId);
            weeklyGroup.setModifyTime(date);
            weeklyGroup.setModifyUser(userName);
            weeklyGroup.setStatus(CommonConstants.STATUS_OPEN);
            groupService.add(weeklyGroup);
            return restSimpleSuccess();
        } catch (Exception e) {
            logger.error("添加组别信息失败");
            e.printStackTrace();
            return new RestFulVO(WeeklyRetEnum.ERROR);
        }
    }

    /**
     * 查询全部有效的组别信息
     *
     * @return
     */
    @PostMapping("/group/allGroupOnStatus")
    public RestFulVO allGroupOnStatus() {
        List<WeeklyGroup> groups = groupService.allGroup();
        List<String> strings = new ArrayList<>();
        for (WeeklyGroup group : groups) {
            if (group.getStatus().equals(CommonConstants.STATUS_OPEN)) {
                strings.add(group.getGroupName());
            }
        }
        return restSuccessByList(strings);
    }

    /**
     * 查询全部的组别信息分页显示
     *
     * @return
     */
    @PostMapping("/group/pageList")
    public RestFulVO pageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize) {
        logger.info("当前组别页码：{}", pageNum);
        PageDetail<WeeklyGroupDto> dtos = groupService.pageList(pageNum, pageSize);
        return restSuccess(dtos);
    }

    /**
     * 根据组别名称查询组别信息分页显示
     *
     * @return
     */
    @PostMapping("/group/pageListByGroupName")
    public RestFulVO pageListByGroupName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize,
                                         @RequestParam(value = "groupName") String groupName) {
        if (groupName == null) {
            logger.error("根据组别名称查询组别信息分页显示参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        logger.info("当前组别页码：{},查询组别信息：{}", pageNum, groupName);
        PageDetail<WeeklyGroupDto> dtos = groupService.pageListByGroupName(pageNum, pageSize, groupName);
        return restSuccess(dtos);
    }


    /**
     * 更新组别信息
     *
     * @param groupId
     * @param groupName
     * @param token
     * @return
     */
    @PostMapping("/group/updateGroup")
    public RestFulVO updateGroup(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token) {
        if (groupId == null || groupName == null || token == null) {
            logger.error("更新组别信息参数为空");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        try {
            Date date = new Date();
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.error("用户token失效，{}", token);
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            String userName = userToken.getUserName();
            Integer userId = userToken.getUserId();
            WeeklyGroup weeklyGroup = new WeeklyGroup();
            weeklyGroup.setGroupId(groupId);
            weeklyGroup = groupService.selectById(weeklyGroup);
            weeklyGroup.setGroupName(groupName);
            weeklyGroup.setModifyId(userId);
            weeklyGroup.setModifyTime(date);
            weeklyGroup.setModifyUser(userName);
            groupService.update(weeklyGroup);
            return restSimpleSuccess();
        } catch (Exception e) {
            logger.error("更新组别信息失败");
            e.printStackTrace();
            return new RestFulVO(WeeklyRetEnum.ERROR);
        }
    }

    /**
     * 修改组别状态
     *
     * @param groupId
     * @return
     */
    @PostMapping("/group/updateGroupStatus")
    public RestFulVO updateGroupStatus(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "status") String status, @RequestParam(value = "token") String token) {
        if (groupId == null || status == null || token == null) {
            logger.error("修改组别状态参数为空");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        UserToken userToken = null;
        Date date = null;
        try {
            userToken = JwtUtils.getInfoFromToken(token);
            date = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userToken == null) {
            logger.error("用户token失效，{}", token);
            return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
        }
        WeeklyGroup weeklyGroup = new WeeklyGroup();
        weeklyGroup.setGroupId(groupId);
        weeklyGroup = groupService.selectById(weeklyGroup);
        if (weeklyGroup != null) {
            weeklyGroup.setStatus(Integer.parseInt(status));
            weeklyGroup.setModifyId(userToken.getUserId());
            weeklyGroup.setModifyTime(date);
            weeklyGroup.setModifyUser(userToken.getUserName());
            groupService.update(weeklyGroup);
            return restSimpleSuccess();
        } else {
            logger.error("该组别不存在：{}", groupId);
            return new RestFulVO(WeeklyRetEnum.GROUP_NOT_EXIST);
        }
    }

    /**
     * 根据groupName查找组别信息
     *
     * @param groupName
     * @return
     */
    @PostMapping("/group/findGroupByName")
    public RestFulVO findGroupByName(@RequestParam(value = "groupName") String groupName) {
        if (groupName == null) {
            logger.error("根据组别名称查询组别信息参数为空");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        List<WeeklyGroup> groupList = groupService.findByName(groupName);
        if (groupList != null && groupList.size() > 0) {
            return restSuccess(groupList.get(0));
        } else {
            return restSimpleFault();
        }
    }

    /**
     * 根据id查找组别信息
     *
     * @param groupId
     * @return
     */
    @PostMapping("/group/findById")
    public RestFulVO findById(@RequestParam(value = "groupId") Integer groupId) {
        if (groupId == null) {
            logger.error("根据组别id查询组别信息参数为空");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        WeeklyGroup weeklyGroup = new WeeklyGroup();
        weeklyGroup.setGroupId(groupId);
        weeklyGroup = groupService.selectById(weeklyGroup);
        return restSuccess(weeklyGroup);
    }
}
