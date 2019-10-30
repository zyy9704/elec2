package com.itszt.elec8.utils;


import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.domain.ElecPopedom;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.TreeSet;

/**
 * Created by Administrator on 2018/3/26.
 */
public class CheckPopeTag extends SimpleTagSupport {
    private String have;

    public String getHave() {
        return have;
    }

    public void setHave(String have) {
        this.have = have;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        TreeSet<ElecPopedom> elecPopedoms= (TreeSet<ElecPopedom>) session.getAttribute(ConstantsKeys.SESSION_POPES_NOW);
        boolean canHave=false;
        for (ElecPopedom elecPopedom : elecPopedoms) {
            if (elecPopedom.getMid().equals(have)) {
                canHave=true;
                break;
            }
        }
        if (canHave){
//            让自定义标签中的信息显示出来
            getJspBody().invoke(null);
        }


        

    }
}
