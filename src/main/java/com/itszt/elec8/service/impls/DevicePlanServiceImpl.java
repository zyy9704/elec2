package com.itszt.elec8.service.impls;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.controller.vo.DevPlanSearchVo;
import com.itszt.elec8.dao.DDLDao;
import com.itszt.elec8.dao.DevicePlanDao;
import com.itszt.elec8.domain.ElecDevicePlan;
import com.itszt.elec8.domain.ElecExportfields;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DevicePlanService;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zyy on 2019/8/14.
 */
@Service
public class DevicePlanServiceImpl implements DevicePlanService {
    @Resource
    private DevicePlanDao devicePlanDao;
    @Resource
    private DDLDao ddlDao;
    private static final String IMPORT_MODEL="所属单位#设备名称#计划时间#设备类型#品牌#型号#用途#数量#单位#金额#";
    @Override
    public List<ElecDevicePlan> getAllPlans() {
        List<ElecDevicePlan> elecDevicePlans = devicePlanDao.queryAllPlans();
        return elecDevicePlans;
    }

    @Override
    public PageBean<ElecDevicePlan> getPageDatasIndex(HttpServletRequest request) {

        UtilPageBean utilPageBean=new UtilPageBean(request);
        long counts = devicePlanDao.queryCounts();
        utilPageBean.setTotalResult((int) counts);

        int startPos = utilPageBean.getBeginResult();

        List<ElecDevicePlan> elecDevicePlans = devicePlanDao.queryPagePlans(startPos, UtilPageBean.PAGE_CAPACITY);
        utilPageBean.setBeans(elecDevicePlans);

        PageBean pageBean = utilPageBean.getPageBean();
        pageBean.setRequestUrl("/planPage.html");
        return pageBean;
    }

    @Override
    public PageBean<ElecDevicePlan> getPageDatasCondition(HttpServletRequest request, DevPlanSearchVo devPlanSearchVo) {

        UtilPageBean utilPageBean=new UtilPageBean(request);
        long counts = devicePlanDao.queryCountsByCondition(devPlanSearchVo.getJctId(),devPlanSearchVo.getDevName(),devPlanSearchVo.getPlanDatef(),devPlanSearchVo.getPlanDatet(),devPlanSearchVo.getDevType());
        utilPageBean.setTotalResult((int) counts);

        int startPos = utilPageBean.getBeginResult();

        List<ElecDevicePlan> elecDevicePlans = devicePlanDao.queryPagePlansByCondition(startPos, UtilPageBean.PAGE_CAPACITY,devPlanSearchVo.getJctId(),devPlanSearchVo.getDevName(),devPlanSearchVo.getPlanDatef(),devPlanSearchVo.getPlanDatet(),devPlanSearchVo.getDevType());
        utilPageBean.setBeans(elecDevicePlans);

        PageBean pageBean = utilPageBean.getPageBean();
        pageBean.setRequestUrl("/queryData.html");
        return pageBean;
    }

    @Override
    public List<ExportSettingItem> getDefaultExportItems() {
        ElecExportfields defaultPlanEXSetting = devicePlanDao.queryDefaultPlanEXSetting();
        String dbNameStr = defaultPlanEXSetting.getExpfieldname();
        String excelNameStr = defaultPlanEXSetting.getExpnamelist();
        if (dbNameStr==null) {

        }
        String[] dbNames = dbNameStr.split("#");
        String[] excelNames = excelNameStr.split("#");
        if (dbNames==null||dbNames.length<=0){

        }
        if (dbNames.length!=excelNames.length){

        }
        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i <dbNames.length ; i++) {
            String dbName=dbNames[i];
            String excelName=excelNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            exportSettingItems.add(exportSettingItem);
        }
        return exportSettingItems;
    }

    @Override
    public List<ExportSettingItem> getDefaultNoExportItems() {
        ElecExportfields defaultPlanEXSetting = devicePlanDao.queryDefaultPlanEXSetting();

        String dbNameStr = defaultPlanEXSetting.getNoexpfieldname();
        String excelNameStr = defaultPlanEXSetting.getNoexpnamelist();
        if (dbNameStr==null) {

        }
        String[] dbNames = dbNameStr.split("#");
        String[] excelNames = excelNameStr.split("#");
        if (dbNames==null||dbNames.length<=0){

        }
        if (dbNames.length!=excelNames.length){

        }
        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i <dbNames.length ; i++) {
            String dbName=dbNames[i];
            String excelName=excelNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            exportSettingItems.add(exportSettingItem);
        }
        return exportSettingItems;
    }

    @Override
    public void saveExportSetting(String names, String fields, String nonames, String nofields, HttpSession session) {
        String[] excelNames = names.split("#");
        String[] dbNames = fields.split("#");
        String[] noexcelNames = nonames.split("#");
        String[] nodbNames = nofields.split("#");

        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        for (int i = 0; i <dbNames.length ; i++) {
            String dbName=dbNames[i];
            String excelName=excelNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            exportSettingItems.add(exportSettingItem);
        }


        List<ExportSettingItem> noExportSettingItems=new ArrayList<>();
        for (int i = 0; i <nodbNames.length ; i++) {
            String dbName=nodbNames[i];
            String excelName=noexcelNames[i];
            ExportSettingItem exportSettingItem=new ExportSettingItem();
            exportSettingItem.setDbName(dbName);
            exportSettingItem.setExcelName(excelName);
            noExportSettingItems.add(exportSettingItem);
        }

        session.setAttribute(ConstantsKeys.SESSION_PLAN_EX_ITEM,exportSettingItems);
        session.setAttribute(ConstantsKeys.SESSION_PLAN_NOEX_ITEM,noExportSettingItems);

    }

    @Override
    public byte[] getExportFile(DevPlanSearchVo devPlanSearchVo, List<ExportSettingItem> exportSettingItems) throws Exception {
        List<ElecDevicePlan> elecDevicePlans = devicePlanDao.queryPlansByCondition(devPlanSearchVo.getJctId(),devPlanSearchVo.getDevName(),devPlanSearchVo.getPlanDatef(),devPlanSearchVo.getPlanDatet(),devPlanSearchVo.getDevType());

        //创建工作簿
        XSSFWorkbook workBook = new XSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        XSSFSheet sheet = workBook.createSheet("设备购置计划");

        //向sheet中创建行列
        //标题行
        XSSFRow rowTitle = sheet.createRow(0);
        for (int i = 0; i < exportSettingItems.size(); i++) {
            XSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(exportSettingItems.get(i).getExcelName());

        }


        //数据行
        for (int i = 0; i < elecDevicePlans.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);

            ElecDevicePlan elecDevicePlan = elecDevicePlans.get(i);

            for (int j = 0; j < exportSettingItems.size(); j++) {
                XSSFCell cell = row.createCell(j);
                String dbName = exportSettingItems.get(j).getDbName();
                String fieldName = dbName.toLowerCase();

               Field field= ElecDevicePlan.class.getDeclaredField(fieldName);
               field.setAccessible(true);
                cell.setCellValue(field.get(elecDevicePlan).toString());
            }

        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        workBook.write(byteArrayOutputStream);

        workBook.close();//最后记得关闭工作簿

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String importPlans(MultipartFile file) {
        //读取工作簿
        XSSFWorkbook workBook = null;
        //        校验1：非法格式不处理
        try {
            workBook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return "无法解析";
        }
        //读取工作表
        XSSFSheet sheet = workBook.getSheetAt(0);

        //        校验2：不合乎模板不处理
        XSSFRow rowTitle = sheet.getRow(0);
        int rowTitleOfCells = rowTitle.getPhysicalNumberOfCells();
        StringBuilder stringBuilder=new StringBuilder();
        for (int j = 0; j <rowTitleOfCells ; j++) {
            XSSFCell cell = rowTitle.getCell(j);
            if (cell==null) {
                return "导入模板错误，请重新下载模板上传数据！！";
            }
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            String stringCellValue = rowTitle.getCell(j).getStringCellValue();
            stringBuilder.append(stringCellValue);
            stringBuilder.append("#");
        }
        if (!stringBuilder.toString().trim().equals(IMPORT_MODEL)){
            return "导入文件有误";
        }

        //        校验3：不合乎业务逻辑不处理
        List<ElecSystemddl> jctids = ddlDao.quaryDatasByKeyword("所属单位");
        StringBuilder resultInfo=new StringBuilder();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int rowNums = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowNums; i++) {
            XSSFRow row = sheet.getRow(i);
            int columnNum = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columnNum; j++) {
                row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
                String stringCellValue = row.getCell(j).getStringCellValue().trim();

                switch (j){
//                    判断所属单位
                    case (0):
                        boolean contains=false;
                        for (ElecSystemddl jctid : jctids) {
                            if (jctid.getDdlname().equals(stringCellValue)){
                                contains=true;
                            }
                        }
                        if (!contains){
                            resultInfo.append("第 ");
                            resultInfo.append(i+1);
                            resultInfo.append(" 行,第 ");
                            resultInfo.append(j+1);
                            resultInfo.append(" 列,所属单位并不存在 <br/>");
                        }

                        break;
                    case (2):
//                        判断计划时间
                        Date date=new Date();
                        try {
                            date=simpleDateFormat.parse(stringCellValue);
                            Calendar calendar2=Calendar.getInstance();
                            calendar2.setTime(date);
                            boolean before = calendar.before(calendar2);
                            if (!before){
                                resultInfo.append("第 ");
                                resultInfo.append(i+1);
                                resultInfo.append(" 行,第 ");
                                resultInfo.append(j+1);
                                resultInfo.append(" 列,计划时间必须在今天之后 <br/>");
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                            resultInfo.append("第 ");
                            resultInfo.append(i+1);
                            resultInfo.append(" 行,第 ");
                            resultInfo.append(j+1);
                            resultInfo.append(" 列,时间格式有误 <br/>");
                        }

                        break;

                }

            }

        }

        if (resultInfo.length()==0){
            for (int i = 1; i < rowNums; i++) {
                XSSFRow row = sheet.getRow(i);
                int columnNum = row.getPhysicalNumberOfCells();
                List<String> params=new ArrayList<>();
                for (int j = 0; j < columnNum; j++) {
                    row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
                    String stringCellValue = row.getCell(j).getStringCellValue().trim();
                    params.add(stringCellValue);
                }
                devicePlanDao.insertPlans(params.get(0),params.get(1),params.get(2),params.get(3),params.get(4),params.get(5),params.get(6),params.get(7),params.get(8),params.get(9));
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
}
