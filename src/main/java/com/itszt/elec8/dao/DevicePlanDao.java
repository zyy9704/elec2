package com.itszt.elec8.dao;

import com.itszt.elec8.domain.ElecDevicePlan;
import com.itszt.elec8.domain.ElecExportfields;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zyy on 2019/8/14.
 */
@Repository
public interface DevicePlanDao {

    @Select("select * from elec_device_plan")
    public List<ElecDevicePlan> queryAllPlans();

    @Select("select count(*) from elec_device_plan")
    public long queryCounts();

    @Select("select * from elec_device_plan limit #{param1},#{param2}")
    public List<ElecDevicePlan> queryPagePlans(int startPos,int size);


    public long queryCountsByCondition(@Param("jctId") String jctId,
           @Param("devName") String devName,
            @Param("planDatef")String planDatef,
             @Param("planDatet") String planDatet,
             @Param("devType")String devType);


    public List<ElecDevicePlan> queryPagePlansByCondition(@Param("startPos")int startPos,
                                                          @Param("size")int size,
                                                          @Param("jctId") String jctId,
                                                          @Param("devName") String devName,
                                                          @Param("planDatef")String planDatef,
                                                          @Param("planDatet") String planDatet,
                                                          @Param("devType")String devType);

    public List<ElecDevicePlan> queryPlansByCondition(@Param("jctId") String jctId,
                                                      @Param("devName") String devName,
                                                      @Param("planDatef")String planDatef,
                                                      @Param("planDatet") String planDatet,
                                                      @Param("devType")String devType);

    @Select("select * from elec_exportfields where belongTo='5-4'")
    public ElecExportfields queryDefaultPlanEXSetting();

    @Insert("insert into elec_device_plan(JctID,DevName,PlanDate,DevType,Trademark,SpecType,Useness,Quality,UseUnit,DevExpense) " +
            "values(#{param1},#{param2},#{param3},#{param4},#{param5},#{param6},#{param7},#{param8},#{param9},#{param10})")
    public void insertPlans(String JctID,String DevName,String PlanDate,String DevType,String Trademark,String SpecType,String Useness,String Quality,String UseUnit,String DevExpense);
}
