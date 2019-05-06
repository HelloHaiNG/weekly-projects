package com.ucarweekly.usercenterservice.controller.web;

import com.ucar.api.service.WeeklyDeatilClient;
import com.ucarweekly.usercenterapi.msg.WeeklyUserCenterRetEnum;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohong
 * @since 2018/11/26 14:12
 */
@RestController
public class WeeklyDetailController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyDeatilClient weeklyDeatilClient;

    /**
     * 根据userid查找周报详情
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/detail/pageListByUserId")
    public RestFulVO pageListByUserId(@RequestParam(value = "token") String token, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        if (token == null || pageNum == null || pageSize == null) {
            logger.info("根据userId查找周报详情参数传递出错");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        com.ucar.api.vo.RestFulVO vo = weeklyDeatilClient.pageListByUserId(token, pageNum, pageSize);
        return new RestFulVO(vo.getRestCode(), vo.getRestMsg(), vo.getData());
    }

    /**
     * 查找个人某一期的周报内容
     *
     * @param weeklyId
     * @param userId
     * @return
     */
    @PostMapping("/detail/weeklyDetailByUser")
    public RestFulVO weeklyDetailByUser(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "userId") Integer userId) {
        if (weeklyId == null || userId == null) {
            logger.info("查看具体周报详情页面参数传递出错");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        com.ucar.api.vo.RestFulVO vo = weeklyDeatilClient.weeklyDetailByUser(weeklyId, String.valueOf(userId));
        return new RestFulVO(vo.getRestCode(), vo.getRestMsg(), vo.getData());
    }

}
