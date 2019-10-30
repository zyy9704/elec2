package com.itszt.elec8.service.impls;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.dao.RoleDao;
import com.itszt.elec8.dao.UserDao;
import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import com.itszt.elec8.service.UserService;
import com.itszt.elec8.utils.MD5Utils;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by zyy on 2019/8/7.
 */
@Service
@EnableCaching
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Override
//    @Cacheable(cacheNames=ConstantsKeys.REDIS_CACHE_MANAGER,key = ConstantsKeys.REDIS_KEY_USER+"+#name"+"+#password")
    public ElecUser login(String name,String password) {
        System.out.println("UserServiceImpl.login");
        ElecUser elecUser = userDao.queryAllUser(name, password);

        List<ElecRole> elecRoles = userDao.queryUserRole(elecUser.getUserid());
        TreeSet allpopes=new TreeSet();
        for (ElecRole elecRole:elecRoles){
            List<ElecPopedom> popesByRoleID = roleDao.queryPopesByRoleID(elecRole.getRoleid());
            allpopes.addAll(popesByRoleID);
        }


        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(ConstantsKeys.SESSION__USER_NOW,elecUser);
        session.setAttribute(ConstantsKeys.SESSION_ROLES_NOW,elecRoles);
        session.setAttribute(ConstantsKeys.SESSION_POPES_NOW,allpopes);

        return elecUser;
    }
}
