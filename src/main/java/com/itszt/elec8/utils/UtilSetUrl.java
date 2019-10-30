package com.itszt.elec8.utils;

import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * Created by zyy on 2019/8/11.
 */
public class UtilSetUrl {
    public static void setUserManageUrl(List<ElecRole> elecRoles , ElecPopedom elecPopedom){
        ElecRole elecRole = elecRoles.get(0);
        Integer roleID = NumberUtils.createInteger(elecRole.getRoleid());

        if (roleID<=3) {
//            管理员
            elecPopedom.setUrl("/userIndex.html");
        }else {
//            一般用户
            elecPopedom.setUrl("/userEdit.html");
        }
    }
}
