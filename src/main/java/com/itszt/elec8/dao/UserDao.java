package com.itszt.elec8.dao;

import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zyy on 2019/8/7.
 */
@Repository
public interface UserDao {
    @Select("select * from elec_user where logonName=#{param1} and logonPwd=#{param2}")
    public ElecUser queryAllUser(String name,String password);

    @Select("")
    public ElecPopedom queryUserPope(String userid);

    @Select("select * from elec_role left join elec_role_user on elec_role.roleID=elec_role_user.roleid where userid=#{userid}")
    public List<ElecRole> queryUserRole(String userid);

}
