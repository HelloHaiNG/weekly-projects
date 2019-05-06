package com.ucar.api.hystrix;

import com.ucar.api.dto.WeeklyReportDto;
import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.service.WeeklyReportClient;
import com.ucar.api.vo.RestFulVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liaohong
 * @since 2018/11/26 10:25
 */
@Component
public class WeeklyReportClientFallbackFactory implements FallbackFactory<WeeklyReportClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public WeeklyReportClient create(Throwable throwable) {
        return new WeeklyReportClient() {
            @Override
            public RestFulVO updateStatus(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "status") Integer status, @RequestParam(value = "token") String token) {
                logger.info("调用weekly服务更新周报状态出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO addWeeklyReport(String weeklyName, String token) throws Exception {
                logger.info("调用weekly服务添加周报出错");
                return new RestFulVO(WeeklyRetEnum.ADD_WEEKLYREPORT_ERROR);
            }

            @Override
            public RestFulVO pageList(Integer pageNum, Integer pageSize) {
                logger.info("调用weekly服务分页显示周报基本信息出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO findById(Integer weeklyId) {
                logger.info("调用weekly服务根据id查找周报信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO updateDownloadCount(Integer weeklyId) {
                logger.info("调用weekly服务更新周报下载数接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO findWeeklyByName(Integer pageNum, Integer pageSize, String weeklyName) {
                logger.info("调用weekly服务根据周报名称查询周报信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }
        };
    }
}
