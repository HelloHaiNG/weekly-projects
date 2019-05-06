package com.ucarweekly.usercenterservice.base;
import com.ucarweekly.usercenterapi.msg.WeeklyUserCenterRetEnum;
import com.ucarweekly.usercenterapi.vo.RestFulVO;

import java.util.Collection;
import java.util.List;

/**
 * Created by nico on 2018/7/29.
 */
public class BaseRest<T> {

    /**
     * 返回处理成功后的结果集
     *
     * @return
     */
    public RestFulVO restSuccessByList(List<T> obj) {
        RestFulVO vo = setSystemCode(WeeklyUserCenterRetEnum.SUCCESS.getCode());
        vo.setData(obj);
        return vo;
    }

    /**
     * 返回处理成功后的结果对象
     *
     * @param obj
     * @return
     */
    public RestFulVO restSuccessByObj(T obj) {
        RestFulVO vo = setSystemCode(WeeklyUserCenterRetEnum.SUCCESS.getCode());
        vo.setData(obj);
        return vo;
    }

    public RestFulVO restSuccess(Object obj) {
        RestFulVO vo = setSystemCode(WeeklyUserCenterRetEnum.SUCCESS.getCode());
        if (obj instanceof Collection) {
            vo.setData((List<T>) obj);
        } else {
            vo.setData(obj);
        }
        return vo;
    }

    /**
     * 返回简单处理结果
     *
     * @return
     */
    public RestFulVO restSimpleSuccess() {
        return setSystemCode(WeeklyUserCenterRetEnum.SUCCESS.getCode());
    }

    /**
     * 错误返回
     *
     * @return
     */
    public RestFulVO restSimpleFault() {
        return setSystemCode(WeeklyUserCenterRetEnum.ERROR.getCode());
    }

    /**
     * 错误返回
     *
     * @return
     */
    public RestFulVO restSimpleFault(String errorCode) {
        return setSystemCode(errorCode);
    }

    /**
     * 根据系统编码返回系统结果描述
     *
     * @param systemCode
     * @return
     */
    private RestFulVO setSystemCode(String systemCode) {
        RestFulVO vo = new RestFulVO();
        vo.setRestCode(systemCode);
        vo.setRestMsg(WeeklyUserCenterRetEnum.getMsgByCode(systemCode));
        return vo;
    }
}
