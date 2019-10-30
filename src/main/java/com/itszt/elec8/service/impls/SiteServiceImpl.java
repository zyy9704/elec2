package com.itszt.elec8.service.impls;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.controller.vo.AddSiteInfoVo;
import com.itszt.elec8.controller.vo.SiteInfoSearchVo;
import com.itszt.elec8.dao.DDLDao;
import com.itszt.elec8.dao.SiteDao;
import com.itszt.elec8.domain.ElecExportfields;
import com.itszt.elec8.domain.ElecStation;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.SiteService;
import com.itszt.elec8.service.bo.ExportSettingItem;
import com.itszt.elec8.utils.doPage.PageBean;
import com.itszt.elec8.utils.doPage.UtilPageBean;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zyy on 2019/8/28.
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteDao siteDao;
    @Resource
    private DDLDao ddlDao;

    @Override
    public PageBean<ElecStation> getAllSiteInfo(HttpServletRequest request) {
        UtilPageBean utilPageBean=new UtilPageBean(request);
        long counts = siteDao.queryAllSiteCount();
        utilPageBean.setTotalResult((int) counts);

        int startPos = utilPageBean.getBeginResult();

        List<ElecStation> elecDevicePlans = siteDao.queryAllSiteInfo(startPos, UtilPageBean.PAGE_CAPACITY);
        utilPageBean.setBeans(elecDevicePlans);

        PageBean pageBean = utilPageBean.getPageBean();
        pageBean.setRequestUrl("/siteInfoPage.html");
        return pageBean;

    }

    @Override
    public PageBean<ElecStation> getPartSiteInfo(SiteInfoSearchVo siteInfoSearchVo, HttpServletRequest request) {

        UtilPageBean utilPageBean=new UtilPageBean(request);
        long counts = siteDao.queryPartSiteCount(siteInfoSearchVo.getJct(),siteInfoSearchVo.getStationName(),siteInfoSearchVo.getStationCode(),siteInfoSearchVo.getStationType(),siteInfoSearchVo.getContactType(),siteInfoSearchVo.getAttributionGround());
        utilPageBean.setTotalResult((int) counts);

        int startPos = utilPageBean.getBeginResult();

        List<ElecStation> elecDevicePlans = siteDao.queryPartSiteInfo(startPos, UtilPageBean.PAGE_CAPACITY,siteInfoSearchVo.getJct(),siteInfoSearchVo.getStationName(),siteInfoSearchVo.getStationCode(),siteInfoSearchVo.getStationType(),siteInfoSearchVo.getContactType(),siteInfoSearchVo.getAttributionGround());
        utilPageBean.setBeans(elecDevicePlans);

        PageBean pageBean = utilPageBean.getPageBean();
        pageBean.setRequestUrl("/siteInfoPartPage.html");
        return pageBean;

    }

    @Override
    public String addSiteInfo(AddSiteInfoVo addSiteInfoVo) {
        try {
            siteDao.insertSiteInfo(addSiteInfoVo.getJctId(),addSiteInfoVo.getStationName(),addSiteInfoVo.getStationCode(),addSiteInfoVo.getUseStartDate(),addSiteInfoVo.getJcfrequency(),addSiteInfoVo.getProduceHome(),addSiteInfoVo.getContactType(),
                    addSiteInfoVo.getStationType(),addSiteInfoVo.getAttributionGround(),addSiteInfoVo.getComment());
            return "插入成功";
        } catch (SQLException e) {
            e.printStackTrace();
            return "插入失败";
        }
    }

    @Override
    public List<ExportSettingItem> getDefaultExportInfo() {
        ElecExportfields elecExportfields = siteDao.queryDefaultInfo();
        String dbNameStr = elecExportfields.getExpfieldname();
        String excelNameStr = elecExportfields.getExpnamelist();
        String[] excelNames = excelNameStr.split("#");
        String[] dbNames = dbNameStr.split("#");
        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i < excelNames.length; i++) {
            String excelName = excelNames[i];
            String dbName = dbNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            exportSettingItems.add(exportSettingItem);
        }
        return exportSettingItems;
    }

    @Override
    public List<ExportSettingItem> getDefaultNoExportInfo() {
        ElecExportfields elecExportfields = siteDao.queryDefaultInfo();
        String excelNameStr = elecExportfields.getNoexpnamelist();
        String dbNameStr = elecExportfields.getNoexpfieldname();
        String[] excelNames = excelNameStr.split("#");
        String[] dbNames = dbNameStr.split("#");
        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i < excelNames.length; i++) {
            String excelName = excelNames[i];
            String dbName = dbNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            exportSettingItems.add(exportSettingItem);
        }
        return exportSettingItems;
    }

    @Override
    public String saveSiteInfoSetting(String expNameList, String expFieldName, String noExpNameList, String noExpFieldName, HttpSession session) {
        String[] dbNames = expFieldName.split("#");
        String[] excelNames = expNameList.split("#");
        String[] noexcelNames = noExpNameList.split("#");
        String[] nodbNames = noExpFieldName.split("#");

        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i < dbNames.length; i++) {
            String dbName = dbNames[i];
            String excelName = excelNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setExcelName(excelName);
            exportSettingItem.setDbName(dbName);
            exportSettingItems.add(exportSettingItem);
        }

        List<ExportSettingItem> noExportSettingItems=new ArrayList<>();
        for (int i = 0; i < nodbNames.length; i++) {
            String nodbName = nodbNames[i];
            String noexcelName = noexcelNames[i];
            ExportSettingItem noexportSettingItem=new ExportSettingItem();
            noexportSettingItem.setExcelName(noexcelName);
            noexportSettingItem.setDbName(nodbName);
            noExportSettingItems.add(noexportSettingItem);
        }

        session.setAttribute(ConstantsKeys.SESSION_SITE_INFO_EX_ITEM,exportSettingItems);
        session.setAttribute(ConstantsKeys.SESSION_SITE_INFO_NOEX_ITEM,noExportSettingItems);


        return "保存成功";
    }

    @Override
    public byte[] ExportSiteInfo(SiteInfoSearchVo siteInfoSearchVo,List<ExportSettingItem> exportSettingItems) throws Exception {
        List<ElecStation> elecStations = siteDao.queryExportSiteInfoByContition(siteInfoSearchVo.getJct(), siteInfoSearchVo.getStationName(), siteInfoSearchVo.getStationCode(), siteInfoSearchVo.getStationType(), siteInfoSearchVo.getContactType(), siteInfoSearchVo.getAttributionGround());

        //创建工作簿
        XSSFWorkbook workBook = new XSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        XSSFSheet sheet = workBook.createSheet("站点信息表");

        //向sheet中创建行列
        //标题行
        XSSFRow rowTitle = sheet.createRow(0);
        for (int i = 0; i < exportSettingItems.size(); i++) {
            XSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(exportSettingItems.get(i).getExcelName());
        }


        //数据行
        for (int i = 0; i < elecStations.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);

            ElecStation elecStation = elecStations.get(i);


            for (int j = 0; j < exportSettingItems.size(); j++) {
                XSSFCell cell = row.createCell(j);
                String dbName = exportSettingItems.get(j).getDbName();
                String fieldName = dbName.toLowerCase();
                Field field=ElecStation.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                cell.setCellValue(field.get(elecStation).toString());
            }

        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        workBook.write(byteArrayOutputStream);

        workBook.close();//最后记得关闭工作簿


        return byteArrayOutputStream.toByteArray();


    }

    public static final String IMPORT_MODEL="站点名称#站点类别#所属单位#站点代号#生产厂家#通讯方式#安装地点#";
    @Override
    public String ImportSiteInfo(MultipartFile file) {
        //读取工作簿
        XSSFWorkbook workBook = null;
        try {
            //        校验一：非法格式文件不处理
            workBook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return "导入文件格式不符合要求";
        }
        //读取工作表
        XSSFSheet sheet = workBook.getSheetAt(0);

        //        校验二：文档内容必须合乎规范


        XSSFRow rowTitle = sheet.getRow(0);
        int rowTitleCells = rowTitle.getPhysicalNumberOfCells();
        StringBuilder stringBuilderTitle=new StringBuilder();
        for (int i = 0; i <rowTitleCells ; i++) {
            XSSFCell cell = rowTitle.getCell(i);
            if (cell==null) {
                return "导入文件内容不符合模板，请重新上传";
            }
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            String stringCellValue = rowTitle.getCell(i).getStringCellValue();
            stringBuilderTitle.append(stringCellValue);
            stringBuilderTitle.append("#");
        }
        if (!stringBuilderTitle.toString().trim().equals(IMPORT_MODEL)){
            return "导入文件内容不符合模板，请重新上传";
        }

        //       校验三： 单个单元格中的数据必须合乎业务规范
        //总行数
        int rowNums = sheet.getPhysicalNumberOfRows();
        List<ElecSystemddl> jctids = ddlDao.quaryDatasByKeyword("所属单位");
        StringBuilder resultInfo=new StringBuilder();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 1; i <rowNums ; i++) {
            XSSFRow row = sheet.getRow(i);
            int cells = row.getPhysicalNumberOfCells();
            for (int j = 0; j <cells ; j++) {
                row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
                String stringCellValue = row.getCell(j).getStringCellValue().trim();

                switch (j){
                    case 2:
                        boolean contains=false;
                        for (ElecSystemddl jctid : jctids) {
                            if (jctid.getDdlname().equals(stringCellValue)) {
                                contains=true;
                            }
                        }
                        if(!contains){
                            resultInfo.append("第 ");
                            resultInfo.append(i+1);
                            resultInfo.append(" 行,第 ");
                            resultInfo.append(j+1);
                            resultInfo.append(" 列，所属单位并不存在！请修正！<br/>");

                        }
                        break;
                }

            }

        }

        if (resultInfo.length()==0){
            for (int i = 1; i <rowNums ; i++) {
                XSSFRow row = sheet.getRow(i);
                List<String> params=new ArrayList<>();
                int columnNum = row.getPhysicalNumberOfCells();
                for (int j = 0; j <columnNum ; j++) {
                    row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
                    String stringCellValue = row.getCell(j).getStringCellValue().trim();
                    params.add(stringCellValue);
                }
                siteDao.insertSiteInfo(params.get(0),params.get(1),params.get(2),params.get(3),params.get(4),params.get(5),params.get(6));

            }
            resultInfo.append("导入成功");
        }

        try {
            workBook.close();//最后记得关闭工作簿
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultInfo.toString();

    }

    @Override
    public ElecStation getSiteInfo(String stationid) {

        return siteDao.querySiteInfo(stationid);
    }

    @Override
    public String modify(String stationId,AddSiteInfoVo addSiteInfoVo) {
        try {
            siteDao.deleteSiteInfo(stationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "删除失败";
        }

        try {
            siteDao.modifySiteInfo(addSiteInfoVo.getJctId(),addSiteInfoVo.getStationName(),addSiteInfoVo.getStationCode(),addSiteInfoVo.getUseStartDate(),
                    addSiteInfoVo.getJcfrequency(),addSiteInfoVo.getProduceHome(),addSiteInfoVo.getContactType(),addSiteInfoVo.getStationType(),addSiteInfoVo.getAttributionGround(),addSiteInfoVo.getComment(),stationId);

        } catch (SQLException e) {
            e.printStackTrace();
            return "添加失败";
        }

        return "修改成功";


    }

    @Override
    public String deleteSiteInfo(String stationId) {
        try {
            siteDao.deleteSiteInfo(stationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "删除失败";
        }
        return "删除成功";
    }
}
