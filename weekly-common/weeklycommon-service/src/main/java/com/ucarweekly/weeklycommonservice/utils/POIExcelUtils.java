package com.ucarweekly.weeklycommonservice.utils;

import com.ucarweekly.usercenterapi.dto.FrontUserDto;
import com.ucarweekly.usercenterapi.service.FrontUserClient;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import com.ucarweekly.weeklycommonservice.constants.CommonConstants;
import com.ucarweekly.weeklycommonservice.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author liaohong
 * @since 2018/11/27 9:28
 */
public class POIExcelUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public void creatSheet(XSSFWorkbook workbook, String sheetName) {
        workbook.createSheet(sheetName);
    }

    public void writeExcel(XSSFWorkbook workbook, String content, XSSFRow row, Integer cellIndex) {
        XSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(content);
        autoBr(workbook, cell);
    }

    public void excelTemplet(XSSFWorkbook workbook, XSSFSheet sheet, List<String> list1, List<String> list2,
                             List<String> list3, List<String> list4, List<String> list5) {
        //写标题栏
        XSSFRow row = sheet.createRow(0);
        logger.info("开始写入周报标题栏....");
        for (int i = 0; i < CommonConstants.CELL_LENGTH; i++) {
            writeExcel(workbook, list1.get(i), row, i);
        }
        //写内容
        logger.info("开始写入周报详情......");
        for (int i = 0; i < list2.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < CommonConstants.CELL_LENGTH; j++) {
                if (j == 0) {
                    writeExcel(workbook, list2.get(i), row, j);
                } else if (j == 1) {
                    writeExcel(workbook, list3.get(i), row, j);
                } else if (j == 2) {
                    writeExcel(workbook, list4.get(i), row, j);
                } else {
                    writeExcel(workbook, list5.get(i), row, j);
                }
            }
        }
    }

    public String path(String fileName) throws IOException {
        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView
                .getFileSystemView();
        File file = fsv.getHomeDirectory();
        String path = file.getAbsolutePath();
        String fileType = ".xlsx";
        String date = dateFormat.format(new Date());
        date = StringUtils.replace(date, "-", "");
        date = StringUtils.replace(date, " ", "");
        date = StringUtils.replace(date, ":", "");
        String excelPath = path + "\\" + fileName + date + fileType;
        File file1 = new File(excelPath);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        return excelPath;
    }

    /**
     * 自动调整列宽
     *
     * @param workbook
     */
    public void autoAdjustCellWidth(XSSFWorkbook workbook) {
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int j = 0; j < rows; j++) {
                XSSFRow row = sheet.getRow(j);
                int cells = row.getPhysicalNumberOfCells();
                for (int k = 0; k < cells; k++) {
                    sheet.autoSizeColumn((short) k);
                }
            }
        }
    }

    /**
     * 自动换行
     */
    public void autoBr(XSSFWorkbook workbook, XSSFCell cell) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
    }

}
