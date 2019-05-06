package com.ucar.api.service;

import com.ucar.api.WeeklyConst;
import com.ucar.api.hystrix.WeeklyGroupClientFallbackFactory;
import com.ucar.api.vo.RestFulVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

/**
 * @author liaohong
 * @since 2018/12/18 14:49
 */
@FeignClient(value = WeeklyConst.Weekly_SERVICE_NAME, fallbackFactory = WeeklyGroupClientFallbackFactory.class)
public interface WeeklyGroupClient {

    /**
     * 添加组别信息
     *
     * @param groupName
     * @param token
     * @return
     * @throws ParseException
     */
    @PostMapping("/group/addGroup")
    RestFulVO addGroup(@RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token);

    /**
     * 更新组别信息
     *
     * @param groupId
     * @param groupName
     * @param token
     * @return
     */
    @PostMapping("/group/updateGroup")
    RestFulVO updateGroup(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "groupName") String groupName, @RequestParam(value = "token") String token);


    /**
     * 根据groupName查找组别信息
     * @param groupName
     * @return
     */
    @PostMapping("/group/findGroupByName")
    RestFulVO findGroupByName(@RequestParam(value = "groupName")String groupName);

    /**
     * 查询全部有效的组别信息
     *
     * @return
     */
    @PostMapping("/group/allGroupOnStatus")
    RestFulVO allGroupOnStatus();

    /**
     * 根据id查找组别信息
     * @param groupId
     * @return
     */
    @PostMapping("/group/findById")
    RestFulVO findById(@RequestParam(value = "groupId")Integer groupId);

    /**
     * 查询全部的组别信息分页显示
     *
     * @return
     */
    @PostMapping("/group/pageList")
    RestFulVO pageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize);

    /**
     * 根据组别名称查询组别信息分页显示
     *
     * @return
     */
    @PostMapping("/group/pageListByGroupName")
    RestFulVO pageListByGroupName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize,
                                         @RequestParam(value = "groupName") String groupName);

    /**
     * 修改组别状态
     *
     * @param groupId
     * @return
     */
    @PostMapping("/group/updateGroupStatus")
    RestFulVO updateGroupStatus(@RequestParam(value = "groupId") Integer groupId, @RequestParam(value = "status") String status, @RequestParam(value = "token") String token);
}
