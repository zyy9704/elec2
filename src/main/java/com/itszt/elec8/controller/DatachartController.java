package com.itszt.elec8.controller;

import com.itszt.elec8.domain.ElecFileupload;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DDLService;
import com.itszt.elec8.service.DatachartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zyy on 2019/8/16.
 */
@Controller
public class DatachartController {

    @Resource
    private DDLService ddlService;
    @Resource
    private DatachartService datachartService;
    @RequestMapping("dataChartIndex")
    public String dataChartIndex(Model model){
        List<ElecSystemddl> jctids = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> types = ddlService.getKeywordDatas("图纸类别");
        model.addAttribute("jctids",jctids);
        model.addAttribute("types",types);
        return "dataChart/dataChartIndex";
    }


    @RequestMapping("dataChartAdd")
    public String dataChartAdd(Model model){
        List<ElecSystemddl> jctids = ddlService.getKeywordDatas("所属单位");
        List<ElecSystemddl> types = ddlService.getKeywordDatas("图纸类别");
        model.addAttribute("jctids",jctids);
        model.addAttribute("types",types);

        return "dataChart/dataChartAdd";
    }

    @RequestMapping("uploadFiles")
    public String uploadFiles(MultipartFile[] uploads,String[] comments,String projId,String belongTo,Model model){
        String resultInfo = datachartService.uploadFiles(uploads, comments, projId, belongTo);
        model.addAttribute("resultInfo",resultInfo);
        return "forward:/dataChartAdd.html";
    }

    @RequestMapping("queryAddData")
    public String queryAddData(String projId,String belongTo,Model model){
        List<ElecFileupload> fileuploads = datachartService.queryAddData(projId, belongTo);
        model.addAttribute("fileuploads",fileuploads);
        return "dataChart/dataChartAddList";
    }

    @RequestMapping("queryDatas")
    public String queryDatas(String projId,String belongTo,String queryString,Model model){
        List<ElecFileupload> fileuploads = datachartService.queryDatas(projId, belongTo, queryString);
        model.addAttribute("fileuploads",fileuploads);
        return "forward:/dataChartIndex.html";
    }

}
