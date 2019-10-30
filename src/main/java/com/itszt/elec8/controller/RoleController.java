package com.itszt.elec8.controller;

import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import com.itszt.elec8.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zyy on 2019/8/10.
 */
@Controller
public class RoleController {
    @Resource
    private RoleService roleService;
    @RequestMapping("roleIndex")
    public String roleIndex(Model model){
        List<ElecPopedom> allPopes = roleService.getAllPopes();
        List<ElecRole> allRoles = roleService.getAllRoles();

        model.addAttribute("allPopes",allPopes);
        model.addAttribute("allRoles",allRoles);
        return "system/roleIndex";
    }

    @RequestMapping("getRoleData")
    public String getRoleData(String roleID,Model model){
        List<ElecPopedom> rolePopes = roleService.getRolePopes(roleID);
        List<ElecUser> roleUsers = roleService.getRoleUsers(roleID);
        model.addAttribute("rolePopes",rolePopes);
        model.addAttribute("roleUsers",roleUsers);
        return "system/roleEdit";
    }

    @RequestMapping("modifyRole")
    public String modifyRole(String roleID,String[] selectoper,String[] selectuser,Model model){

        if (selectoper.length==0){
            model.addAttribute("resultInfo","权限不能为空");
            return "forward:/roleIndex.html";
        }

        try {
            roleService.modifyRole(roleID,selectoper,selectuser);
            model.addAttribute("resultInfo","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("resultInfo","修改失败");
        }
        return "forward:/roleIndex.html";
    }

}
