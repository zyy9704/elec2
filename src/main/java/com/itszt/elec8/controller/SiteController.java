package com.itszt.elec8.controller;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.controller.vo.AddSiteInfoVo;
import com.itszt.elec8.controller.vo.SiteInfoSearchVo;
import com.itszt.elec8.domain.ElecStation;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DDLService;
import com.itszt.elec8.service.DatachartService;
import com.itszt.elec8.service.SiteService;
import com.itszt.elec8.service.bo.ExportSettingItem;
import com.itszt.elec8.utils.DateTimeUtil;
import com.itszt.elec8.utils.doPage.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyy on 2019/8/28.
 */
@Controller
public class SiteController {
    @Resource
    private DDLService ddlService;

    @Resource
    private SiteService siteService;

    @RequestMapping("siteInfoIndex")
    public String siteInfoIndex(Model model, HttpServletRequest request){
        List<ElecSystemddl> jctid = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> stationName = ddlService.getKeywordDatas("站点名称");
        List<ElecSystemddl> stationType = ddlService.getKeywordDatas("站点类别");

        PageBean<ElecStation> PageBean = siteService.getAllSiteInfo(request);
        model.addAttribute("pageBean",PageBean);



        model.addAttribute("jctid",jctid);
        model.addAttribute("stationName",stationName);
        model.addAttribute("stationType",stationType);

        return "siteEquapment/siteInfoIndex";
    }

    @RequestMapping("siteInfoPage")
    public String siteInfoPage(Model model, HttpServletRequest request){
        PageBean<ElecStation> PageBean = siteService.getAllSiteInfo(request);
        model.addAttribute("pageBean",PageBean);
        return "siteEquapment/siteInfoPage";
    }

    @RequestMapping("siteInfoPartPage")
    public String siteInfoPartPage(SiteInfoSearchVo siteInfoSearchVo,Model model,HttpServletRequest request){
        PageBean<ElecStation> PageBean = siteService.getPartSiteInfo(siteInfoSearchVo,request);
        model.addAttribute("pageBean",PageBean);
        return "siteEquapment/siteInfoPage";
    }

    @RequestMapping("addSiteInfo")
    public String addSiteInfo(Model model){
        List<ElecSystemddl> jctid = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> stationType = ddlService.getKeywordDatas("站点类别");
        model.addAttribute("jctid",jctid);
        model.addAttribute("stationType",stationType);
        return "siteEquapment/siteInfoAdd";
    }

    @RequestMapping("saveaddSiteInfo")
    public String saveaddSiteInfo(AddSiteInfoVo addSiteInfoVo,Model model){
        String resultInfo = siteService.addSiteInfo(addSiteInfoVo);
        model.addAttribute("resultInfo",resultInfo);
//        return "siteEquapment/siteInfoAdd";
        return "forward:/addSiteInfo.html";
    }

    @RequestMapping("exportSiteInfoSetting")
    public String exportSiteInfoSetting(Model model,HttpSession session){
        Object noex = session.getAttribute(ConstantsKeys.SESSION_SITE_INFO_NOEX_ITEM);
        Object ex = session.getAttribute(ConstantsKeys.SESSION_SITE_INFO_EX_ITEM);
        List<ExportSettingItem> siteExportInfo=new ArrayList<>();
        List<ExportSettingItem> siteNoExportInfo=new ArrayList<>();
        if (noex==null) {
            siteNoExportInfo = siteService.getDefaultNoExportInfo();
        }else{
            siteNoExportInfo= (List<ExportSettingItem>) noex;
        }

        if (ex==null) {
            siteExportInfo = siteService.getDefaultExportInfo();
        }else{
            siteExportInfo= (List<ExportSettingItem>) ex;
        }

        model.addAttribute("siteExportInfo",siteExportInfo);
        model.addAttribute("siteNoExportInfo",siteNoExportInfo);

        return "siteEquapment/siteInfoExport";
    }

    @RequestMapping("saveSiteInfoSetting")
    public String saveSiteInfoSetting(String expNameList, String expFieldName, String noExpNameList, String noExpFieldName, Model model, HttpSession session){
        String resultInfo = siteService.saveSiteInfoSetting(expNameList, expFieldName, noExpNameList, noExpFieldName,session);
        model.addAttribute("resultInfo",resultInfo);
        return "forward:/exportSiteInfoSetting.html";
    }

    @RequestMapping("downLoadSiteInfo")
    public void downLoadSiteInfo(SiteInfoSearchVo siteInfoSearchVo, HttpSession session,  HttpServletResponse response,HttpServletRequest request){
        Object ex = session.getAttribute(ConstantsKeys.SESSION_SITE_INFO_EX_ITEM);
        List<ExportSettingItem> exportSettingItems=new ArrayList<>();
        if (ex==null) {
            exportSettingItems = siteService.getDefaultExportInfo();
        }else{
            exportSettingItems= (List<ExportSettingItem>) ex;
        }

        //设置一下响应
        response.setCharacterEncoding("UTF-8");
        try {
            byte[] bytes = siteService.ExportSiteInfo(siteInfoSearchVo, exportSettingItems);

            //处理文件名的中文  设备计划表(2019-05-19).xlsx
            String fileName="站点信息表("+ DateTimeUtil.getNow_YYYY_MM_DD()+").xlsx";

            String userAgent = request.getHeader("User-Agent");
            //针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }

            //设置为附件类型
            response.setHeader("content-disposition", "attachment;filename="+fileName);

            response.getOutputStream().write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("网络异常，请重试");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    @RequestMapping("ImportSiteInfoFile")
    public String ImportSiteInfoFile(){

        return "siteEquapment/siteInfoImport";
    }

    @RequestMapping("siteInfoImport")
    public String siteInfoImport(MultipartFile files,Model model){
        String resultInfo = siteService.ImportSiteInfo(files);
        model.addAttribute("resultInfo",resultInfo);

        return "siteEquapment/siteInfoImport";
    }

    @RequestMapping("siteInfoView")
    public String siteInfoView(String stationid,Model model){
        List<ElecSystemddl> jctid = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> stationType = ddlService.getKeywordDatas("站点类别");
        model.addAttribute("jctid",jctid);
        model.addAttribute("stationType",stationType);

        ElecStation siteInfoView = siteService.getSiteInfo(stationid);
        model.addAttribute("siteInfoView",siteInfoView);

        return "siteEquapment/siteInfoView";
    }

    @RequestMapping("siteInfoEdit")
    public String siteInfoEdit(String stationId,Model model){
        List<ElecSystemddl> jctid = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> stationType = ddlService.getKeywordDatas("站点类别");
        model.addAttribute("jctid",jctid);
        model.addAttribute("stationType",stationType);

        ElecStation siteInfoView = siteService.getSiteInfo(stationId);
        model.addAttribute("siteInfo",siteInfoView);
        return "siteEquapment/siteInfoEdit";
    }

    @RequestMapping("EditSiteInfo")
    public String EditSiteInfo(String stationId,Model model,AddSiteInfoVo addSiteInfoVo){
        String resultInfo = siteService.modify(stationId, addSiteInfoVo);

        model.addAttribute("resultInfo",resultInfo);
        model.addAttribute("stationId",stationId);
        return "siteEquapment/siteInfoEdit";
    }

    @RequestMapping("deleteSiteInfo")
    public String deleteSiteInfo(String stationid){
        String resultInfo = siteService.deleteSiteInfo(stationid);
        return "forward:/siteInfoPage.html";
    }


}
