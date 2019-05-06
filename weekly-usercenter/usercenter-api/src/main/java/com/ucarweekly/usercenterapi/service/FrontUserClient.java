package com.ucarweekly.usercenterapi.service;

import com.ucarweekly.usercenterapi.WeeklyUserCenterConst;
import com.ucarweekly.usercenterapi.hystrix.FrontUserClientFallbackFactory;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/27 10:33
 */
@FeignClient(value = WeeklyUserCenterConst.WeeklyUserCenter_SERVICE_NAME, fallbackFactory = FrontUserClientFallbackFactory.class)
public interface FrontUserClient {

    /**
     * 根据UserId查找用户信息
     * @param userId
     * @return
     */
    @PostMapping("/front/findByUserId")
    RestFulVO findByUserId(@RequestParam(value = "userId") Integer userId);

    /**
     * 根据token查找用户信息
     *
     * @param token
     * @return
     */
    @PostMapping("/frontUser/findByUserIdToken")
    RestFulVO findByUserIdToken(@RequestParam(value = "token") String token);

    /**
     * 更新组别信息
     *
     * @param token
     * @param groupId
     * @return
     */
    @PostMapping("/front/updateGroupId")
    RestFulVO updateGroupId(@RequestParam(value = "token") String token, @RequestParam(value = "groupId") Integer groupId);
}
