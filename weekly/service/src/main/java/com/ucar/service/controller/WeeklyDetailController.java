package com.ucar.service.controller;

import com.ucar.api.dto.UserToken;
import com.ucar.api.dto.WeeklyDetailDto;
import com.ucar.api.msg.WeeklyRetEnum;
import com.ucar.api.vo.PageDetail;
import com.ucar.api.vo.RestFulVO;
import com.ucar.service.base.BaseRest;
import com.ucar.service.constants.CommonConstants;
import com.ucar.service.entities.WeeklyDetail;
import com.ucar.service.service.RedisService;
import com.ucar.service.service.WeeklyDetailService;
import com.ucar.service.service.WeeklyReportService;
import com.ucar.service.utils.JwtUtils;
import com.ucarweekly.usercenterapi.service.FrontUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author liaohong
 * @since 2018/11/23 16:59
 */
@RestController
public class WeeklyDetailController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeeklyDetailService detailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private WeeklyReportService weeklyReportService;

    @Autowired
    private FrontUserClient frontUserClient;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 写周报
     *
     * @param weeklyId
     * @param weeklyName
     * @param thisWeekContent
     * @param nextWeekContent
     * @param trouble
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/detail/addWeeklyDetail")
    public RestFulVO addWeeklyDetail(@RequestParam(value = "weeklyId") Integer weeklyId,
                                     @RequestParam(value = "weeklyName") String weeklyName,
                                     @RequestParam(value = "thisWeekContent") String thisWeekContent,
                                     @RequestParam(value = "nextWeekContent") String nextWeekContent,
                                     @RequestParam(value = "trouble") String trouble,
                                     @RequestParam(value = "token") String token) throws Exception {
        if (weeklyId == null || token == null || weeklyName == null) {
            logger.info("编辑周报是页面传参出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        RestFulVO vo = weeklyReportService.enableEditor(weeklyId);
        if (!vo.getRestCode().equals("0000")) {
            logger.info("当前周报不可被编辑,{}", weeklyId);
            return vo;
        } else {
            UserToken userToken = null;
            try {
                userToken = JwtUtils.getInfoFromToken(token);
            } catch (Exception e) {
                logger.info("token失效");
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            com.ucarweekly.usercenterapi.vo.RestFulVO vo1 = frontUserClient.findByUserId(userToken.getUserId());
            if (!vo1.getRestCode().equals("0000")) {
                logger.error("书写周报时用户信息查询失败，{}", userToken.getUserId());
                return new RestFulVO(WeeklyRetEnum.WEEKLYDETAIL_EDITOR_ERROR);
            }
            HashMap hashMap = (HashMap) vo1.getData();
            Set<String> set = hashMap.keySet();
            Integer groupId = 0;
            for (String string : set) {
                if (string.equals("groupId")) {
                    groupId = Integer.parseInt(hashMap.get(string).toString());
                    break;
                }
            }
            if (groupId.equals(0)) {
                logger.error("用户没有指定组别，{}", userToken.getUserName());
                return new RestFulVO(WeeklyRetEnum.USER_NOT_GROUP);
            }
            WeeklyDetail detail = new WeeklyDetail();
            WeeklyDetailDto dto = new WeeklyDetailDto();
            dto.setTrouble(trouble);
            dto.setNextWeekContent(nextWeekContent);
            dto.setThisWeekContent(thisWeekContent);
            dto.setWeeklyId(weeklyId);
            dto.setWeeklyName(weeklyName);
            BeanUtils.copyProperties(dto, detail);
            String dateString = dateFormat.format(new Date());
            Date date = dateFormat.parse(dateString);
            detail.setEditorTime(date);
            detail.setUserId(userToken.getUserId());
            detail.setUserName(userToken.getUserName());
            detail.setGroupId(groupId);
            RestFulVO vo2 = weeklyDetailByUser(weeklyId, token);
            if (vo2.getRestCode().equals("0000")) {
                logger.info("您已经写了该周报");
                return new RestFulVO(WeeklyRetEnum.WEEKLY_HAS_WRITE);
            } else {
                boolean result = detailService.add(detail);
                if (result) {
                    logger.info("周报书写成功");
                    String weeklyContent = CommonConstants.THIS + thisWeekContent + CommonConstants.NEXT + nextWeekContent
                            + CommonConstants.TROUBLE + trouble;
                    //将周报详情写入redis方便后面导出到报表
                    String ok1 = CommonConstants.PRE_WEEKLY + dto.getWeeklyId().toString() + "_" + String.valueOf(groupId);
                    String ok2 = CommonConstants.PRE_WEEKLY_USER + userToken.getUserId().toString();
                    String ok3 = CommonConstants.PRE_WEEKLY_GROUP + String.valueOf(groupId);
                    redisService.putForHash(ok1, userToken.getUserId().toString(), weeklyContent);
//                    redisService.putForHash(ok1, ok3, weeklyContent);
//                    redisService.putForHash(ok2, dto.getWeeklyName(), weeklyContent);
                    return new RestFulVO(WeeklyRetEnum.SUCCESS);
                } else {
                    logger.info("周报书写失败");
                    return new RestFulVO(WeeklyRetEnum.WEEKLYDETAIL_EDITOR_ERROR);
                }
            }
        }
    }

    /**
     * 根据userId查找周报详情
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/detail/pageListByUserId")
    public RestFulVO pageListByUserId(@RequestParam(value = "token") String token, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) throws Exception {
        if (token == null || pageNum == null || pageSize == null) {
            logger.info("根据userId查找周报详情参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        UserToken userToken = JwtUtils.getInfoFromToken(token);
        if (userToken == null) {
            logger.info("token失效或者不存在");
        }
        Integer userId = userToken.getUserId();
        Example example = new Example(WeeklyDetail.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.orderBy("editorTime").desc();
        PageDetail details = detailService.pageList(pageNum, pageSize, example);
        return restSuccess(details);
    }

    /**
     * 查找个人某一期的周报内容
     *
     * @param weeklyId
     * @param token
     * @return
     */
    @PostMapping("/detail/weeklyDetailByUser")
    public RestFulVO weeklyDetailByUser(@RequestParam(value = "weeklyId") Integer weeklyId, @RequestParam(value = "token") String token) {
        if (weeklyId == null || token == null) {
            logger.info("查看具体周报详情页面参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.error("token失效");
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            List<WeeklyDetail> details = detailService.details(weeklyId, userToken.getUserId());
            if (details != null && details.size() > 0) {
                return restSuccess(details.get(0));
            }
            logger.info("您还未写该期周报,{}", weeklyId);
            return new RestFulVO(WeeklyRetEnum.WEEKLY_NOT_YET_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restSimpleFault();
    }

    /**
     * 根据周报名称查找周报信息
     *
     * @param pageNum
     * @param pageSize
     * @param weeklyName
     * @param token
     * @return
     */
    @PostMapping("/detail/findWeeklyByName")
    public RestFulVO findWeeklyByName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "13") Integer pageSize,
                                      @RequestParam(value = "weeklyName") String weeklyName, @RequestParam(value = "token") String token) {
        if (weeklyName == null || token == null) {
            logger.error("根据周报名称查找周报信息参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.error("token失效");
                return new RestFulVO(WeeklyRetEnum.TOEKN_INVALID);
            }
            WeeklyDetail weeklyDetail = new WeeklyDetail();
            weeklyDetail.setWeeklyName(weeklyName);
            Example example = new Example(WeeklyDetail.class);
            example.orderBy("editorTime").desc();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId", userToken.getUserId());
            weeklyName = "%".concat(weeklyName).concat("%");
            criteria.andLike("weeklyName", weeklyName);
            logger.info("当前页码：{}", pageNum);
            PageDetail<WeeklyDetailDto> detail = detailService.pageList(pageNum, pageSize, example);
            return restSuccess(detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restSimpleFault();
    }

    /**
     * 根据id批量查找周报详情信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/deatail/findByIds")
    public RestFulVO findById(@RequestParam(value = "ids[]") String[] ids) {
        if (ids == null || ids.length == 0) {
            logger.error("根据id批量查找周报详情信息参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        List<WeeklyDetailDto> detailDtos = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            WeeklyDetail detail = new WeeklyDetail();
            detail.setId(Integer.parseInt(ids[i]));
            detail = detailService.findById(detail);
            WeeklyDetailDto detailDto = new WeeklyDetailDto();
            BeanUtils.copyProperties(detail, detailDto);
            detailDtos.add(detailDto);
        }
        return restSuccessByList(detailDtos);
    }

    /**
     * 根据weeklyid查询周报列表
     * @param weeklyId
     * @return
     */
    @PostMapping("/detail/findByWeeklyId")
    public RestFulVO findByWeeklyId(@RequestParam(value = "weeklyId") String weeklyId) {
        if (weeklyId == null) {
            logger.error("根据weeklyid批量查找周报详情信息参数传递出错");
            return new RestFulVO(WeeklyRetEnum.PARAM_ERROR);
        }
        List<WeeklyDetailDto> detailDtos = new ArrayList<>();
        List<WeeklyDetail> byWeeklyId = detailService.findByWeeklyId(weeklyId);
        for (WeeklyDetail detail : byWeeklyId) {
            WeeklyDetailDto dto = new WeeklyDetailDto();
            BeanUtils.copyProperties(detail, dto);
            detailDtos.add(dto);
        }
        return restSuccessByList(detailDtos);
    }

}
