package com.itszt.elec8.service;

import com.itszt.elec8.controller.vo.AddSiteInfoVo;
import com.itszt.elec8.controller.vo.SiteInfoSearchVo;
import com.itszt.elec8.domain.ElecStation;
import com.itszt.elec8.service.bo.ExportSettingItem;
import com.itszt.elec8.utils.doPage.PageBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zyy on 2019/8/28.
 */
public interface SiteService {

    public PageBean<ElecStation> getAllSiteInfo(HttpServletRequest request);

    public PageBean<ElecStation> getPartSiteInfo(SiteInfoSearchVo siteInfoSearchVo,HttpServletRequest request);

    public String addSiteInfo(AddSiteInfoVo addSiteInfoVo);

    public List<ExportSettingItem> getDefaultExportInfo();

    public List<ExportSettingItem> getDefaultNoExportInfo();

    public String saveSiteInfoSetting(String expNameList, String expFieldName, String noExpNameList, String noExpFieldName, HttpSession session);

    public byte[] ExportSiteInfo(SiteInfoSearchVo siteInfoSearchVo,List<ExportSettingItem> exportSettingItems) throws Exception;

    public String ImportSiteInfo(MultipartFile file);

    public ElecStation getSiteInfo(String stationid);

    public String modify(String stationId,AddSiteInfoVo addSiteInfoVo);

    public String deleteSiteInfo(String stationId);

}
