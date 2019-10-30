package com.itszt.elec8.dao;

import com.itszt.elec8.domain.ElecExportfields;
import com.itszt.elec8.domain.ElecStation;
import com.itszt.elec8.utils.doPage.PageBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by zyy on 2019/8/28.
 */
@Repository
public interface SiteDao {

    @Select("select count(*) from elec_station")
    public long queryAllSiteCount();

    @Select("select * from elec_station limit #{StartPos},#{size}")
    public List<ElecStation> queryAllSiteInfo(@Param("StartPos") int StartPos,@Param("size") int size);

//    @Select("select count(*) from elec_station")
    public long queryPartSiteCount(@Param("jct") String jct,
            @Param("stationName")String stationName,
            @Param("stationCode")String stationCode,
            @Param("stationType")String stationType,
            @Param("contactType")String contactType,
            @Param("attributionGround")String attributionGround);

//    @Select("select * from elec_station limit #{StartPos},#{size}")
    public List<ElecStation> queryPartSiteInfo(@Param("startPos") int StartPos,@Param("size") int size,
                                               @Param("jct") String jct,
                                               @Param("stationName")String stationName,
                                               @Param("stationCode")String stationCode,
                                               @Param("stationType")String stationType,
                                               @Param("contactType")String contactType,
                                               @Param("attributionGround")String attributionGround);

    @Insert("insert into elec_station values(null,#{jctId},#{stationCode},#{stationName},#{jcfrequency},#{produceHome},#{contactType}," +
            "#{useStartDate},#{comment},'0','admin',#{useStartDate},'admin',#{useStartDate},#{stationType},#{attributionGround},'1')")
    public void insertSiteInfo( @Param("jctId") String jctId,
             @Param("stationName")String stationName,
             @Param("stationCode")String stationCode,
             @Param("useStartDate")Date useStartDate,
             @Param("jcfrequency")String jcfrequency,
             @Param("produceHome")String produceHome,
             @Param("contactType")String contactType,
             @Param("stationType")String stationType,
             @Param("attributionGround")String attributionGround,
             @Param("comment")String comment) throws SQLException;

    @Select("select * from elec_exportfields where belongTo='5-6'")
    public ElecExportfields queryDefaultInfo();

    public List<ElecStation> queryExportSiteInfoByContition( @Param("jct") String jct,
           @Param("stationName") String stationName,
           @Param("stationCode") String stationCode,
           @Param("stationType") String stationType,
           @Param("contactType") String contactType,
           @Param("attributionGround") String attributionGround);

    @Insert("insert into elec_station(StationID,StationName,StationType,JctID,StationCode,ProduceHome,ContactType,JCFrequency)" +
            "values(null,#{StationName},#{StationType},#{JctID},#{StationCode},#{ProduceHome},#{ContactType},#{JCFrequency})")
    public void insertSiteInfo(@Param("StationName") String StationName,
                               @Param("StationType") String StationType,
                               @Param("JctID") String JctID,
                               @Param("StationCode") String StationCode,
                               @Param("ProduceHome") String ProduceHome,
                               @Param("ContactType") String ContactType,
                               @Param("JCFrequency") String JCFrequency);

    @Select("select * from elec_station where StationID=#{stationid}")
    public ElecStation querySiteInfo(String stationid);

    @Delete("delete from elec_station where StationID=#{stationId}")
    public void deleteSiteInfo(String stationId) throws SQLException;

    @Insert("insert into elec_station values(#{stationId},#{jctId},#{stationCode},#{stationName},#{jcfrequency},#{produceHome},#{contactType}," +
            "#{useStartDate},#{comment},'0','admin',#{useStartDate},'admin',#{useStartDate},#{stationType},#{attributionGround},'1')")
    public void modifySiteInfo(@Param("jctId") String jctId,
                               @Param("stationName")String stationName,
                               @Param("stationCode")String stationCode,
                               @Param("useStartDate")Date useStartDate,
                               @Param("jcfrequency")String jcfrequency,
                               @Param("produceHome")String produceHome,
                               @Param("contactType")String contactType,
                               @Param("stationType")String stationType,
                               @Param("attributionGround")String attributionGround,
                               @Param("comment")String comment,
                               @Param("stationId")String stationId) throws SQLException;



}
