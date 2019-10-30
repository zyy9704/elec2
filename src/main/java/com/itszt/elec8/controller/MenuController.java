package com.itszt.elec8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zyy on 2019/8/8.
 */
@RequestMapping("menu")
@Controller
public class MenuController {
    @RequestMapping("/{path}")
    public String path(@PathVariable("path") String path){
        return "menu/"+path;
    }

}
