package com.ucar.api.hystrix;

import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.service.WeeklyDeatilClient;
import com.ucar.api.vo.RestFulVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/26 13:56
 */
@Component
public class WeeklyDetailClientFallbackFactory implements FallbackFactory<WeeklyDeatilClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public WeeklyDeatilClient create(Throwable throwable) {
        return new WeeklyDeatilClient() {
            @Override
            public RestFulVO pageListByUserId(@RequestParam(value = "token") String token, @RequestParam(value = "pageNum") Integer pageNum,
                                              @RequestParam(value = "pageSize") Integer pageSize) {
                logger.info("调用weekly服务根据id查找周报详情出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO weeklyDetailByUser(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "token") String token) {
                logger.info("调用weekly服务查找个人某一期周报内容接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO findById(String[] ids) {
                logger.info("调用weekly服务根据id批量查找个人周报内容接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO findByWeeklyId(String weeklyId) {
                logger.info("调用weekly服务根据weeklyId查找周报列表接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }
        };
    }
}
