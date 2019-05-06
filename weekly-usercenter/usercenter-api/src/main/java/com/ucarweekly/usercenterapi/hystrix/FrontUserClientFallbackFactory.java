package com.ucarweekly.usercenterapi.hystrix;

import com.ucarweekly.usercenterapi.msg.WeeklyUserCenterRetEnum;
import com.ucarweekly.usercenterapi.service.FrontUserClient;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/27 10:34
 */
@Component
public class FrontUserClientFallbackFactory implements FallbackFactory<FrontUserClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FrontUserClient create(Throwable throwable) {
        return new FrontUserClient() {

            @Override
            public RestFulVO findByUserId(Integer userId) {
                logger.info("调用user center服务根据userid查找用户信息接口失败");
                return new RestFulVO(WeeklyUserCenterRetEnum.ERROR);
            }

            @Override
            public RestFulVO findByUserIdToken(String token) {
                logger.info("调用user center服务根据token查找用户信息接口失败");
                return new RestFulVO(WeeklyUserCenterRetEnum.ERROR);
            }

            @Override
            public RestFulVO updateGroupId(String token, Integer groupId) {
                logger.info("usercenter服务更新个人组别信息接口失败");
                return new RestFulVO(WeeklyUserCenterRetEnum.ERROR);
            }
        };
    }
}
