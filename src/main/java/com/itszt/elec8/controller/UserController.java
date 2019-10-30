package com.itszt.elec8.controller;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import com.itszt.elec8.service.UserService;
import com.itszt.elec8.utils.MD5Utils;
import com.itszt.elec8.utils.UtilSetUrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by zyy on 2019/8/7.
 */
@Controller

public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("login")
    public String login(@RequestParam(required = true) String name, @RequestParam(required = true) String password, Model model){
        ElecUser login = userService.login(name, MD5Utils.md5(password));
        if (login!=null) {
            return "menu/home";
        }else {
            model.addAttribute("resultInfo","失败");
            return "menu/index";
        }
    }
    @RequestMapping("getMenuPopes")
    @ResponseBody
    public List<ElecPopedom> getMenuPopes(HttpSession session){
        TreeSet<ElecPopedom> elecPopedoms = (TreeSet<ElecPopedom>) session.getAttribute(ConstantsKeys.SESSION_POPES_NOW);
        List<ElecPopedom> menuPopes=new ArrayList<>();
        for (ElecPopedom elecPopedom:elecPopedoms){
            if (elecPopedom.getIsmenu()){
                menuPopes.add(elecPopedom);
            }
            if (elecPopedom.getName().equals("用户管理")){
                UtilSetUrl.setUserManageUrl((List<ElecRole>) session.getAttribute(ConstantsKeys.SESSION_ROLES_NOW),elecPopedom);
            }
        }
        return menuPopes;

    }

    @RequestMapping("userIndex")
    public String userIndex(){
        return "system/userIndex";
    }

    @RequestMapping("userEdit")
    public String userEdit(){
        return "system/userEdit";
    }
}
