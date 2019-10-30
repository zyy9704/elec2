package com.itszt.elec8.controller;

import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DDLService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zyy on 2019/8/8.
 */
@Controller
public class DictionaryController {
    @Resource
    DDLService ddlService;
    @RequestMapping("/dictionaryIndex")
    public String dictionaryIndex(Model model){
        List<String> keywords = ddlService.queryAllKeywords();
        model.addAttribute("keywords",keywords);
        return "/system/dictionaryIndex";
    }

    @RequestMapping("getDatas")
    public String getDatas(String keyword,Model model){
        System.out.println("keyword = [" + keyword + "]");
        List<ElecSystemddl> keywordDatas = ddlService.getKeywordDatas(keyword);
        model.addAttribute("keywordDatas",keywordDatas);
        return "/system/dictionaryEdit";
    }

    @RequestMapping("modifyDatas")
    public String modifyDatas(String keyword,String keywordname,String[] itemname){
        boolean empty1 = StringUtils.isEmpty(keyword);
        boolean empty2 = StringUtils.isEmpty(keywordname);
        if (empty1&&!empty2){
//            添加
            try {
                ddlService.addKeyword(keywordname,itemname);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (!empty1&&empty2){
//            修改
            try {
                ddlService.modifyKeyword(keyword,itemname);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "forward:/dictionaryIndex.html";

    }

}
