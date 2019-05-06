package com.ucar.weeklyadminwebservice.controller.web;

import com.ucar.api.service.WeeklyGroupClient;
import com.ucar.weeklyadminwebapi.vo.RestFulVO;
import com.ucar.weeklyadminwebservice.base.BaseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohong
 * @since 2018/12/18 14:57
 */
@RestController
public class WeelyGroupController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyGroupClient weeklyGroupClient;

    /**
     * 添加组别信息
     *
     * @param groupName
     * @param token
     * @return
     */
    @PostMapping("/weeklyadminweb/addGroup")
    public RestFulVO addGroup(@RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token) {
        com.ucar.api.vo.RestFulVO vo = weeklyGroupClient.addGroup(groupName, token);
        RestFulVO restFulVO = new RestFulVO();
        restFulVO.setData(vo.getData());
        restFulVO.setRestCode(vo.getRestCode());
        restFulVO.setRestMsg(vo.getRestMsg());
        return restFulVO;
    }

    /**
     * 更新组别信息
     *
     * @param groupId
     * @param groupName
     * @param token
     * @return
     */
    @PostMapping("/weeklyadminweb/updateGroup")
    public RestFulVO updateGroup(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token) {
        com.ucar.api.vo.RestFulVO vo = weeklyGroupClient.updateGroup(groupId, groupName, token);
        RestFulVO restFulVO = new RestFulVO();
        restFulVO.setData(vo.getData());
        restFulVO.setRestCode(vo.getRestCode());
        restFulVO.setRestMsg(vo.getRestMsg());
        return restFulVO;
    }

    /**
     * 分页查找全部组别
     *
     * @return
     */
    @PostMapping("/weeklyadminweb/pageListGroup")
    public RestFulVO pageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize) {
        com.ucar.api.vo.RestFulVO vo = weeklyGroupClient.pageList(pageNum, pageSize);
        RestFulVO restFulVO = new RestFulVO();
        restFulVO.setData(vo.getData());
        restFulVO.setRestCode(vo.getRestCode());
        restFulVO.setRestMsg(vo.getRestMsg());
        return restFulVO;
    }

    /**
     * 修改组别状态
     * @param groupId
     * @param status
     * @param token
     * @return
     */
    @PostMapping("/weeklyadminweb/updateGroupStatus")
    public RestFulVO updateGroupStatus(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "status") String status, @RequestParam(value = "token") String token) {
        com.ucar.api.vo.RestFulVO vo = weeklyGroupClient.updateGroupStatus(groupId, status, token);
        RestFulVO restFulVO = new RestFulVO();
        restFulVO.setData(vo.getData());
        restFulVO.setRestCode(vo.getRestCode());
        restFulVO.setRestMsg(vo.getRestMsg());
        return restFulVO;
    }

    /**
     * 根据组别名称分页查找组别信息
     * @param pageNum
     * @param pageSize
     * @param groupName
     * @return
     */
    @PostMapping("/weeklyadminweb/pageListByGroupName")
    public RestFulVO pageListByGroupName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize,
                                         @RequestParam(value = "groupName") String groupName) {
        com.ucar.api.vo.RestFulVO vo = weeklyGroupClient.pageListByGroupName(pageNum, pageSize, groupName);
        RestFulVO restFulVO = new RestFulVO();
        restFulVO.setData(vo.getData());
        restFulVO.setRestCode(vo.getRestCode());
        restFulVO.setRestMsg(vo.getRestMsg());
        return restFulVO;
    }
}
