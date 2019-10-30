package com.itszt.elec8.dao;

import com.itszt.elec8.domain.ElecPopedom;
import com.itszt.elec8.domain.ElecRole;
import com.itszt.elec8.domain.ElecUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zyy on 2019/8/10.
 */
@Repository
public interface RoleDao {
    @Select("select * from elec_role")
    public List<ElecRole> queryAllRoles();

    @Select("select * from elec_popedom")
    public List<ElecPopedom> queryAllPopes();

    @Select("select * from elec_role_popedom left join elec_popedom on elec_role_popedom.mid=elec_popedom.mid where roleID=#{roleID}")
    public List<ElecPopedom> queryPopesByRoleID(String roleID);

    @Insert("insert into elec_role_popedom values(#{param1},#{param2})")
    public void insetRolePope(String roleid,String mid);

    @Delete("delete from elec_role_popedom where roleID=#{param1} and mid=#{param2}")
    public void deleteRolePopes(String roleid,String mid);

    @Select("select * from elec_user")
    public List<ElecUser> queryAllUsers();

    @Select("select * from elec_role_user left join elec_user on elec_role_user.userid=elec_user.userID where roleid=#{roleID}")
    public List<ElecUser> queryRoleUsers(String roleID);


    @Insert("insert into elec_role_user values(#{param2},#{param1})")
    public void insetRoleUsers(String roleid,String userid);

    @Delete("delete from elec_role_user where roleID=#{param1} and userid=#{param2}")
    public void deleteRoleusers(String roleid,String userid);



}
