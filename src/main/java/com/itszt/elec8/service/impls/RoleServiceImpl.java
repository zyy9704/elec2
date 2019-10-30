package com.itszt.elec8.service.impls;

import com.itszt.elec8.dao.RoleDao;
import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import com.itszt.elec8.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyy on 2019/8/10.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleDao roleDao;

    @Override
    public List<ElecRole> getAllRoles() {
        return roleDao.queryAllRoles();
    }

    @Override
    public List<ElecPopedom> getAllPopes() {
        return roleDao.queryAllPopes();

    }

    @Override
    public List<ElecPopedom> getRolePopes(String roleID) {
        List<ElecPopedom> popesByRoleID = roleDao.queryPopesByRoleID(roleID);
        List<ElecPopedom> allPopes = roleDao.queryAllPopes();
        for(ElecPopedom popedom:allPopes){
            if (popesByRoleID.contains(popedom)){
                popedom.setIschecked(true);

            }
        }

        return allPopes;
    }

    @Override
    public List<ElecUser> getRoleUsers(String roleID) {
        List<ElecUser> allUsers = roleDao.queryAllUsers();
        List<ElecUser> elecUsers = roleDao.queryRoleUsers(roleID);
        for (ElecUser user:allUsers){
            if (elecUsers.contains(user)){
                user.setIschecked(true);
            }
        }

        return allUsers;
    }

    @Override
    @Transactional
    public void modifyRole(String roleID, String[] selectoper,String[] selectuser) {
//        未修改之前role所拥有的权限
        List<ElecPopedom> popesByRoleIDOld1 = roleDao.queryPopesByRoleID(roleID);
        List<ElecPopedom> popesByRoleIDOld2=new ArrayList<>();
        List<ElecPopedom> popesByRoleIDNew=new ArrayList<>();
        for (ElecPopedom popedom:popesByRoleIDOld1){
            popesByRoleIDOld2.add(popedom);
        }
        for (String s : selectoper) {
            ElecPopedom popedom=new ElecPopedom();
            popedom.setMid(s);
            popesByRoleIDNew.add(popedom);
        }
//        popesByRoleIDOld1有      popesByRoleIDNew 没有  删除
        popesByRoleIDOld1.removeAll(popesByRoleIDNew);


//        反过来  增加
        popesByRoleIDNew.removeAll(popesByRoleIDOld2);

        for(ElecPopedom popedom:popesByRoleIDOld1){
            roleDao.deleteRolePopes(roleID,popedom.getMid());
        }

        for(ElecPopedom popedom:popesByRoleIDNew){
            roleDao.insetRolePope(roleID,popedom.getMid());
        }
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------

        List<ElecUser> elecUsersOld1 = roleDao.queryRoleUsers(roleID);
        List<ElecUser> elecUsersOld2=new ArrayList<>();
        List<ElecUser> elecUsersNew=new ArrayList<>();
        for (ElecUser user:elecUsersOld1){
            elecUsersOld2.add(user);
        }
        for (String s : selectuser) {
            ElecUser user=new ElecUser();
            user.setUserid(s);
            elecUsersNew.add(user);
        }

        elecUsersOld1.removeAll(elecUsersNew);
        elecUsersNew.removeAll(elecUsersOld2);

        for (ElecUser user:elecUsersOld1){
            roleDao.deleteRoleusers(roleID,user.getUserid());
        }

        for (ElecUser user:elecUsersNew){
            roleDao.insetRoleUsers(roleID,user.getUserid());
        }




    }
}
