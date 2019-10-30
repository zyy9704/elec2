package com.itszt.elec8.dao;

import com.itszt.elec8.domain.ElecFileupload;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zyy on 2019/8/17.
 */
@Repository
public interface DatachartDao {

    @Insert("insert into elec_fileupload values(null,#{projid},#{belongto},#{filename},#{fileurl},#{progresstime},#{isdelete},#{createempid},#{createdate},#{comment})")
    @SelectKey(statement = "select last_insert_id()" ,before = false,keyColumn = "SeqID",keyProperty = "seqid",resultType = Integer.class)
    public void insertFileUpload(ElecFileupload elecFileupload) throws SQLException;

    public List<ElecFileupload> queryAddData(@Param("projId") String projId,@Param("belongTo") String belongTo);

}
