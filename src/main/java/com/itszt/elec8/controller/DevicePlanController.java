package com.itszt.elec8.controller;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.controller.vo.DevPlanSearchVo;
import com.itszt.elec8.domain.ElecDevicePlan;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DDLService;
import com.itszt.elec8.service.DevicePlanService;
import com.itszt.elec8.service.bo.ExportSettingItem;
import com.itszt.elec8.utils.DateTimeUtil;
import com.itszt.elec8.utils.doPage.PageBean;
import com.itszt.elec8.utils.doPage.UtilPageBean;
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
import java.util.List;

/**
 * Created by zyy on 2019/8/14.
 */
@Controller
public class DevicePlanController {

    @Resource
    private DevicePlanService devicePlanService;
    @Resource
    private DDLService ddlService;

    @RequestMapping("devicePlanIndex")
    public String devicePlanIndex(Model model, HttpServletRequest request){
        PageBean pageBean = devicePlanService.getPageDatasIndex(request);
//        model.addAttribute("allPlans",allPlans);
//        UtilPageBean utilPageBean=new UtilPageBean(request);
        model.addAttribute("pageBean",pageBean);

        List<ElecSystemddl> jctids = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> devtypes = ddlService.getKeywordDatas("设备类型");
        model.addAttribute("jctids",jctids);
        model.addAttribute("devtypes",devtypes);

        return "equapment/planIndex";

    }

    @RequestMapping("planView")
    public String planView(String devPlanId){
//        自己完成
        return "equapment/planView.jsp";
    }


    @RequestMapping("planPage")
    public String planPageData(Model model, HttpServletRequest request){
        PageBean pageBean = devicePlanService.getPageDatasIndex(request);
        model.addAttribute("pageBean",pageBean);
        return "equapment/planPage";
    }

    @RequestMapping("queryData")
    public String queryData(Model model, HttpServletRequest request, DevPlanSearchVo devPlanSearchVo){
        PageBean pageBean = devicePlanService.getPageDatasCondition(request,devPlanSearchVo);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("condition",devPlanSearchVo);
        return "equapment/planPage";
    }

    @RequestMapping("planExportView")
    public String planExportView(Model model,HttpSession session){
        List<ExportSettingItem> defaultExportItems = null;
        Object ex = session.getAttribute(ConstantsKeys.SESSION_PLAN_EX_ITEM);
        if (ex==null){
            defaultExportItems = devicePlanService.getDefaultExportItems();
        }else {
            defaultExportItems= (List<ExportSettingItem>) ex;
        }

        Object noex = session.getAttribute(ConstantsKeys.SESSION_PLAN_NOEX_ITEM);
        List<ExportSettingItem> defaultNoExportItems = null;
        if (noex==null){
            defaultNoExportItems = devicePlanService.getDefaultNoExportItems();
        }else {
            defaultNoExportItems= (List<ExportSettingItem>) noex;
        }

        model.addAttribute("defaultExportItems",defaultExportItems);
        model.addAttribute("defaultNoExportItems",defaultNoExportItems);
        return "equapment/planExport";
    }

    @RequestMapping("saveExportSetting")
    public String saveExportSetting(String names, String fields, String nonames, String nofields, HttpSession session,Model model){

        devicePlanService.saveExportSetting(names,fields,nonames,nofields,session);

        model.addAttribute("resultInfo","保存成功");
        return "forward:/planExportView.html";
    }

//    导出文件
    @RequestMapping("exportPlans")
    public void exportPlans(Model model, HttpServletRequest request,HttpServletResponse response, DevPlanSearchVo devPlanSearchVo,HttpSession session){

        List<ExportSettingItem> defaultExportItems = null;
        Object ex = session.getAttribute(ConstantsKeys.SESSION_PLAN_EX_ITEM);
        if (ex==null){
            defaultExportItems = devicePlanService.getDefaultExportItems();
        }else {
            defaultExportItems= (List<ExportSettingItem>) ex;
        }
        //设置一下响应
        response.setCharacterEncoding("UTF-8");
        try {
            byte[] bytes = devicePlanService.getExportFile(devPlanSearchVo, defaultExportItems);

            //处理文件名的中文  设备计划表(2019-05-19).xlsx
            String fileName="设备计划表("+ DateTimeUtil.getNow_YYYY_MM_DD()+").xlsx";

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
                response.getWriter().write("您的网络异常，请重试！！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping("planImportView")
    public String planImportView(){
        return "equapment/planImport";
    }

    @RequestMapping("planImport")
    public String planImport(MultipartFile files,Model model){
        String resultInfo = devicePlanService.importPlans(files);
        model.addAttribute("resultInfo",resultInfo);
        return "forward:/planImportView.html";

    }
}
