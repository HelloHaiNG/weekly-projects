package com.ucarweekly.weeklycommonapi.hystrix;

import com.ucarweekly.weeklycommonapi.msg.WeeklyCommonRetEnum;
import com.ucarweekly.weeklycommonapi.service.DownLoadClient;
import com.ucarweekly.weeklycommonapi.vo.RestFulVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author liaohong
 * @since 2018/11/28 10:59
 */
@Component
public class DownLoadClientFallbackFactory implements FallbackFactory<DownLoadClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public DownLoadClient create(Throwable throwable) {
        return new DownLoadClient() {
            @Override
            public RestFulVO weeklyReport(Integer weeklyId,String token) throws Exception {
                logger.info("调用common服务下载周报接口出错");
                return new RestFulVO(WeeklyCommonRetEnum.ERROR);
            }
        };
    }
}
