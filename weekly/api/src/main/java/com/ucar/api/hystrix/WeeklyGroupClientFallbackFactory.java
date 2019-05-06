package com.ucar.api.hystrix;

import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.service.WeeklyGroupClient;
import com.ucar.api.vo.RestFulVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author liaohong
 * @since 2018/12/18 14:49
 */
@Component
public class WeeklyGroupClientFallbackFactory implements FallbackFactory<WeeklyGroupClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public WeeklyGroupClient create(Throwable throwable) {
        return new WeeklyGroupClient() {
            @Override
            public RestFulVO addGroup(String groupName, String token) {
                logger.error("调用weekly服务添加组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO updateGroup(Integer groupId, String groupName, String token) {
                logger.error("调用weekly服务更新组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }


            @Override
            public RestFulVO findGroupByName(String groupName) {
                logger.error("调用weekly服务根据groupName查找组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO allGroupOnStatus() {
                logger.error("调用weekly服务查找全部有效的组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO findById(Integer groupId) {
                logger.error("调用weekly服务根据id查找组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO pageList(Integer pageNum, Integer pageSize) {
                logger.error("调用weekly服务分页显示查找组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO pageListByGroupName(Integer pageNum, Integer pageSize, String groupName) {
                logger.error("调用weekly服务根据组别名称分页显示查找组别信息接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }

            @Override
            public RestFulVO updateGroupStatus(Integer groupId, String status, String token) {
                logger.error("调用weekly服务修改组别状态接口出错");
                return new RestFulVO(WeeklyRetEnum.ERROR);
            }
        };
    }
}
