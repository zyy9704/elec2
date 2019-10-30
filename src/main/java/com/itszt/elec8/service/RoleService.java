package com.itszt.elec8.service;

import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;

import java.util.List;

/**
 * Created by zyy on 2019/8/10.
 */
public interface RoleService {
    public List<ElecRole> getAllRoles();

    public List<ElecPopedom> getAllPopes();

    public List<ElecPopedom> getRolePopes(String roleID);

    public List<ElecUser> getRoleUsers(String roleID);

    public void modifyRole(String roleID,String[] selectoper,String[] selectuser) throws Exception;
}
