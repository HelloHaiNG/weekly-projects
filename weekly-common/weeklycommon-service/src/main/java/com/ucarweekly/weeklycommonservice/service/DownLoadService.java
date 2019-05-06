package com.ucarweekly.weeklycommonservice.service;

import com.ucarweekly.weeklycommonapi.vo.RestFulVO;

/**
 * @author liaohong
 * @since 2018/11/27 14:32
 */
public interface DownLoadService {

    RestFulVO downloadWeeklyReport(Integer weeklyId,String token) throws Exception;

    RestFulVO downloadMyWeeklys(String[] ids,String token) throws Exception;

    RestFulVO downloadByWeeklyId(String weeklyId) throws Exception;
}
