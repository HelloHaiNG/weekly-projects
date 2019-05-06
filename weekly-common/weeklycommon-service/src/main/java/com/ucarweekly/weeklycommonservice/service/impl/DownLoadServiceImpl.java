package com.ucarweekly.weeklycommonservice.service.impl;

import com.ucar.api.service.WeeklyDeatilClient;
import com.ucar.api.service.WeeklyReportClient;
import com.ucarweekly.usercenterapi.service.FrontUserClient;
import com.ucarweekly.weeklycommonapi.dto.UserToken;
import com.ucarweekly.weeklycommonapi.dto.WeeklyReportDto;
import com.ucarweekly.weeklycommonapi.msg.WeeklyCommonRetEnum;
import com.ucarweekly.weeklycommonapi.vo.RestFulVO;
import com.ucarweekly.weeklycommonservice.constants.CommonConstants;
import com.ucarweekly.weeklycommonservice.service.DownLoadService;
import com.ucarweekly.weeklycommonservice.service.RedisService;
import com.ucarweekly.weeklycommonservice.utils.JwtUtils;
import com.ucarweekly.weeklycommonservice.utils.POIExcelUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author liaohong
 * @since 2018/11/27 14:33
 */
@Service
public class DownLoadServiceImpl implements DownLoadService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisService;

    @Autowired
    private FrontUserClient frontUserClient;

    @Autowired
    private WeeklyReportClient weeklyReportClient;

    @Autowired
    private WeeklyDeatilClient weeklyDeatilClient;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    /**
     * 组别周报
     *
     * @param weeklyId
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public RestFulVO downloadWeeklyReport(Integer weeklyId, String token) throws Exception {
        UserToken userToken = null;
        userToken = JwtUtils.getInfoFromToken(token);
        if (userToken == null) {
            logger.error("token失效");
            return new RestFulVO(WeeklyCommonRetEnum.TOEKN_INVILD);
        }
        com.ucarweekly.usercenterapi.vo.RestFulVO userVo = frontUserClient.findByUserIdToken(token);
        HashMap hashMap = (HashMap) userVo.getData();
        Integer groupId = 0;
        Set<String> keySet = hashMap.keySet();
        for (String string : keySet) {
            if (string.equals("groupId")) {
                groupId = Integer.parseInt(hashMap.get(string).toString());
                break;
            }
        }
        if (groupId.equals(0)) {
            logger.error("个人组别未设置");
            return new RestFulVO(WeeklyCommonRetEnum.GROUP_NULL);
        }
        com.ucar.api.vo.RestFulVO vo = weeklyReportClient.findById(weeklyId);
        if (!vo.getRestCode().equals(WeeklyCommonRetEnum.SUCCESS.getCode())) {
            logger.info("周报不存在,{}", weeklyId);
            return new RestFulVO(WeeklyCommonRetEnum.WEEKLYREPORT_NOT_EXIST);
        }
        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        HashMap map = (HashMap) vo.getData();
        Set<String> set = map.keySet();
        for (String string : set) {
            if (string.equals("weeklyName")) {
                weeklyReportDto.setWeeklyName(map.get(string).toString());
            }
        }
        POIExcelUtils excelUtils = new POIExcelUtils();
        XSSFWorkbook workbook = new XSSFWorkbook();
        //生成sheet
        excelUtils.creatSheet(workbook, weeklyReportDto.getWeeklyName());
        logger.info("excel中sheet名为：{}", weeklyReportDto.getWeeklyName());

        String id = weeklyId.toString();
        String key = CommonConstants.PRE_WEEKLY + id + "_" + String.valueOf(groupId);
        Set<Object> keys = redisService.getKeysForHash(key);
        List<Object> values = redisService.getValuesForHash(key);

        List<String> thisWeeklys = new ArrayList<>();
        List<String> nextWeeklys = new ArrayList<>();
        List<String> troubles = new ArrayList<>();
        List<String> weeklyTitles = new ArrayList<>();
        List<String> frontUserNames = new ArrayList<>();
        weeklyTitles.add(CommonConstants.EMPLOYEE_NAME);
        weeklyTitles.add(CommonConstants.THIS_WORK);
        weeklyTitles.add(CommonConstants.NEXT_WORK);
        weeklyTitles.add(CommonConstants.TROUBLE_WORK);

        //用户id信息
        for (Object object : keys) {
            com.ucarweekly.usercenterapi.vo.RestFulVO vo1 = frontUserClient.findByUserId(Integer.parseInt((String) object));
            HashMap map1 = (HashMap) vo1.getData();
            Set<String> set1 = map1.keySet();
            for (String string : set1) {
                if (string.equals("userName")) {
                    frontUserNames.add(map1.get(string).toString());
                }
            }
        }
        //周报内容
        for (int i = 0; i < values.size(); i++) {
            String value = (String) values.get(i);
            String thisWeekly = StringUtils.substringBetween(value, CommonConstants.THIS, CommonConstants.NEXT);
            String nextWeekly = StringUtils.substringBetween(value, CommonConstants.NEXT, CommonConstants.TROUBLE);
            String trouble = StringUtils.substringAfter(value, CommonConstants.TROUBLE);
            thisWeeklys.add(thisWeekly);
            nextWeeklys.add(nextWeekly);
            troubles.add(trouble);
        }
        XSSFSheet sheet = workbook.getSheet(weeklyReportDto.getWeeklyName());
        logger.info("开始写入excel........");
        excelUtils.excelTemplet(workbook, sheet, weeklyTitles, frontUserNames, thisWeeklys, nextWeeklys, troubles);
        //自动调整列宽
        logger.info("自动调整列宽...");
        excelUtils.autoAdjustCellWidth(workbook);
        logger.info("写入文件中...");
        String excelPath = excelUtils.path(weeklyReportDto.getWeeklyName());
        OutputStream stream = new FileOutputStream(excelPath);
        workbook.write(stream);
        stream.close();
        com.ucar.api.vo.RestFulVO vo1 = weeklyReportClient.updateDownloadCount(weeklyId);
        String downloadCount = "";
        HashMap map1 = (HashMap) vo1.getData();
        Set<String> set1 = map1.keySet();
        for (String string : set1) {
            if (string.equals("downloadCount")) {
                downloadCount = map1.get(string).toString();
            }
        }
        String restCode = vo1.getRestCode();
        if (restCode.equals("0000")) {
            logger.info("下载成功.........");
            return new RestFulVO(WeeklyCommonRetEnum.SUCCESS, downloadCount);
        } else {
            logger.error("更新周报下载次数失败，{}", weeklyId);
            return new RestFulVO(WeeklyCommonRetEnum.UPDATE_DOWNLOAD_COUNT_ERROR);
        }
    }

    /**
     * 个人周报
     *
     * @param ids
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public RestFulVO downloadMyWeeklys(String[] ids, String token) throws Exception {
        UserToken userToken = JwtUtils.getInfoFromToken(token);
        if (userToken == null) {
            logger.error("token失效");
            return new RestFulVO(WeeklyCommonRetEnum.TOEKN_INVILD);
        }
        com.ucarweekly.usercenterapi.vo.RestFulVO userVo = frontUserClient.findByUserIdToken(token);
        HashMap hashMap = (HashMap) userVo.getData();
        Integer groupId = 0;
        Set<String> keySet = hashMap.keySet();
        for (String string : keySet) {
            if (string.equals("groupId")) {
                groupId = Integer.parseInt(hashMap.get(string).toString());
                break;
            }
        }
        if (groupId.equals(0)) {
            logger.error("个人组别未设置");
            return new RestFulVO(WeeklyCommonRetEnum.GROUP_NULL);
        }
        List<String> weeklyTitles = new ArrayList<>();
        List<String> weeklyNames = new ArrayList<>();
        List<String> thisWeeklys = new ArrayList<>();
        List<String> nextWeeklys = new ArrayList<>();
        List<String> troubles = new ArrayList<>();
        List<Integer> weeklyIds = new ArrayList<>();
        weeklyTitles.add(CommonConstants.WEEKLY_NAME);
        weeklyTitles.add(CommonConstants.THIS_WORK);
        weeklyTitles.add(CommonConstants.NEXT_WORK);
        weeklyTitles.add(CommonConstants.TROUBLE_WORK);
        com.ucar.api.vo.RestFulVO detailById = weeklyDeatilClient.findById(ids);
        if (!detailById.getRestCode().equals("0000")) {
            logger.error("周报不存在...");
            return new RestFulVO(WeeklyCommonRetEnum.WEEKLYREPORT_NOT_EXIST);
        }
        List<HashMap> hashMapList = (List<HashMap>) detailById.getData();
        for (HashMap hashMap1 : hashMapList) {
            Set<String> set = hashMap1.keySet();
            for (String string : set) {
                if (string.equals("weeklyName")) {
                    weeklyNames.add(hashMap1.get(string).toString());
                } else if (string.equals("thisWeekContent")) {
                    thisWeeklys.add(hashMap1.get(string).toString());
                } else if (string.equals("nextWeekContent")) {
                    nextWeeklys.add(hashMap1.get(string).toString());
                } else if (string.equals("trouble")) {
                    troubles.add(hashMap1.get(string).toString());
                } else if (string.equals("weeklyId")) {
                    weeklyIds.add(Integer.parseInt(hashMap1.get(string).toString()));
                }
            }
        }
        POIExcelUtils excelUtils = new POIExcelUtils();
        XSSFWorkbook workbook = new XSSFWorkbook();
        //生成sheet
        excelUtils.creatSheet(workbook, userToken.getUserName());
        logger.info("excel中sheet名为：{}", userToken.getUserName());
        XSSFSheet sheet = workbook.getSheet(userToken.getUserName());
        logger.info("开始写入excel........");
        excelUtils.excelTemplet(workbook, sheet, weeklyTitles, weeklyNames, thisWeeklys, nextWeeklys, troubles);
        //自动调整列宽
        logger.info("自动调整列宽....");
        excelUtils.autoAdjustCellWidth(workbook);
        logger.info("写入到文件中....");
        String excelPath = excelUtils.path(userToken.getUserName());
        OutputStream stream = new FileOutputStream(excelPath);
        workbook.write(stream);
        stream.close();
        logger.info("写入成功....");
        for (Integer weeklyId : weeklyIds) {
            com.ucar.api.vo.RestFulVO vo1 = weeklyReportClient.updateDownloadCount(weeklyId);
            if (!vo1.getRestCode().equals("0000")) {
                logger.error("更新周报下载次数出错...{}", weeklyId);
                return new RestFulVO(WeeklyCommonRetEnum.UPDATE_DOWNLOAD_COUNT_ERROR);
            }
        }
        return new RestFulVO(WeeklyCommonRetEnum.SUCCESS);
    }

    @Override
    public RestFulVO downloadByWeeklyId(String weeklyId) throws Exception {
        List<String> weeklyTitles = new ArrayList<>();
        List<String> thisWeeklys = new ArrayList<>();
        List<String> nextWeeklys = new ArrayList<>();
        List<String> troubles = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        weeklyTitles.add(CommonConstants.EMPLOYEE_NAME);
        weeklyTitles.add(CommonConstants.THIS_WORK);
        weeklyTitles.add(CommonConstants.NEXT_WORK);
        weeklyTitles.add(CommonConstants.TROUBLE_WORK);
        com.ucar.api.vo.RestFulVO byWeeklyId = weeklyDeatilClient.findByWeeklyId(weeklyId);
        com.ucar.api.vo.RestFulVO byId = weeklyReportClient.findById(Integer.parseInt(weeklyId));
        if (!byId.getRestCode().equals(WeeklyCommonRetEnum.SUCCESS.getCode())) {
            logger.info("周报不存在,{}", weeklyId);
            return new RestFulVO(WeeklyCommonRetEnum.WEEKLYREPORT_NOT_EXIST);
        }
        String weeklyName = "";
        HashMap map = (HashMap) byId.getData();
        Set<String> keySet = map.keySet();
        for (String string : keySet) {
            if (string.equals("weeklyName")) {
                weeklyName = map.get(string).toString();
            }
        }
        List<HashMap> hashMapList = (List<HashMap>) byWeeklyId.getData();
        for (HashMap hashMap1 : hashMapList) {
            Set<String> set = hashMap1.keySet();
            for (String string : set) {
                if (string.equals("thisWeekContent")) {
                    thisWeeklys.add(hashMap1.get(string).toString());
                } else if (string.equals("nextWeekContent")) {
                    nextWeeklys.add(hashMap1.get(string).toString());
                } else if (string.equals("trouble")) {
                    troubles.add(hashMap1.get(string).toString());
                } else if (string.equals("userName")) {
                    usernames.add(hashMap1.get(string).toString());
                }
            }
        }
        POIExcelUtils excelUtils = new POIExcelUtils();
        XSSFWorkbook workbook = new XSSFWorkbook();
        //生成sheet
        excelUtils.creatSheet(workbook, weeklyName);
        logger.info("excel中sheet名为：{}", weeklyName);
        XSSFSheet sheet = workbook.getSheet(weeklyName);
        logger.info("开始写入excel........");
        excelUtils.excelTemplet(workbook, sheet, weeklyTitles, usernames, thisWeeklys, nextWeeklys, troubles);
        //自动调整列宽
        logger.info("自动调整列宽....");
        excelUtils.autoAdjustCellWidth(workbook);
        logger.info("写入到文件中....");
        String excelPath = excelUtils.path(weeklyName);
        OutputStream stream = new FileOutputStream(excelPath);
        workbook.write(stream);
        stream.close();
        logger.info("写入成功....");
        com.ucar.api.vo.RestFulVO vo1 = weeklyReportClient.updateDownloadCount(Integer.parseInt(weeklyId));
        String downloadCount = "";
        HashMap map1 = (HashMap) vo1.getData();
        Set<String> set1 = map1.keySet();
        for (String string : set1) {
            if (string.equals("downloadCount")) {
                downloadCount = map1.get(string).toString();
            }
        }
        String restCode = vo1.getRestCode();
        if (restCode.equals("0000")) {
            logger.info("更新{}周报下载次数成功.........", weeklyName);
            return new RestFulVO(WeeklyCommonRetEnum.SUCCESS, downloadCount);
        } else {
            logger.error("更新周报下载次数失败，{}", weeklyName);
            return new RestFulVO(WeeklyCommonRetEnum.UPDATE_DOWNLOAD_COUNT_ERROR);
        }
    }
}
