package com.itszt.elec8.service;

import com.itszt.elec8.controller.vo.DevPlanSearchVo;
import com.itszt.elec8.domain.ElecDevicePlan;
import com.itszt.elec8.service.bo.ExportSettingItem;
import com.itszt.elec8.utils.doPage.PageBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zyy on 2019/8/14.
 */
public interface DevicePlanService {
    public List<ElecDevicePlan> getAllPlans();

    public PageBean<ElecDevicePlan> getPageDatasIndex(HttpServletRequest request);

    public PageBean<ElecDevicePlan> getPageDatasCondition(HttpServletRequest request, DevPlanSearchVo devPlanSearchVo);

    public List<ExportSettingItem> getDefaultExportItems();

    public List<ExportSettingItem> getDefaultNoExportItems();

    public void saveExportSetting(String names, String fields, String nonames, String nofields, HttpSession session);

    public byte[] getExportFile(DevPlanSearchVo devPlanSearchVo,List<ExportSettingItem> exportSettingItems) throws Exception;

    public String importPlans(MultipartFile file);
}
