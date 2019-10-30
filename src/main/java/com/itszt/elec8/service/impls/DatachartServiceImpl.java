package com.itszt.elec8.service.impls;

import com.itszt.elec8.dao.DatachartDao;
import com.itszt.elec8.domain.ElecFileupload;
import com.itszt.elec8.service.DatachartService;
import com.itszt.elec8.utils.DateTimeUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyy on 2019/8/17.
 */
@Service
public class DatachartServiceImpl implements DatachartService {
    private static final String UPLOAD_DIR_ROOT="D:\\javaTest\\uploadfiles";
    private static final String FILE_URL_BASE="/elecData";

    @Resource
    private DatachartDao datachartDao;
    @Resource
    private HttpSolrServer solrServer;
    @Override
    @Transactional
    public String uploadFiles(MultipartFile[] uploads, String[] comments, String projId, String belongTo) {
        String dirs = genDirs(projId, belongTo);
//        文件转储
        List<MultipartFile> filesOK=new ArrayList<>();
        String resultInfo=null;
        for (MultipartFile upload : uploads) {
            String filePath=dirs+upload.getOriginalFilename();
            try {
                upload.transferTo(new File(filePath));
                filesOK.add(upload);
            } catch (IOException e) {
                e.printStackTrace();
                resultInfo= "该文件上传失败"+upload.getOriginalFilename()+"<br/>";
            }
        }
//        记录数据库
        for (int i = 0; i < filesOK.size(); i++) {
            MultipartFile file = filesOK.get(i);
            String comment=comments[i];
            String filename = file.getOriginalFilename();
            String fileUrl=FILE_URL_BASE+"/"+projId+"/"+belongTo+"/"+filename;
            String uploadDate= DateTimeUtil.getNow_YYYY_MM_DD();
            ElecFileupload elecFileupload=new ElecFileupload();
            elecFileupload.setProjid(projId);
            elecFileupload.setBelongto(belongTo);
            elecFileupload.setFilename(filename);
            elecFileupload.setFileurl(fileUrl);
            elecFileupload.setProgresstime(uploadDate);
            elecFileupload.setIsdelete("0");
            elecFileupload.setCreateempid("admin");
            elecFileupload.setCreatedate(new Date());
            elecFileupload.setComment(comment);

            try {
//                插入数据库
                datachartDao.insertFileUpload(elecFileupload);

//                solr搜索库中添加

                SolrInputDocument solrInputDocument=new SolrInputDocument();
                solrInputDocument.addField("id",elecFileupload.getSeqid());
                solrInputDocument.addField("ProjID",projId);
                solrInputDocument.addField("BelongTo",belongTo);
                solrInputDocument.addField("FileName",filename);
                solrInputDocument.addField("FileURL",fileUrl);
                solrInputDocument.addField("ProgressTime",uploadDate);
                solrInputDocument.addField("IsDelete","0");
                solrInputDocument.addField("CreateEmpID","admin");
                solrInputDocument.addField("CreateDate",uploadDate);
                solrInputDocument.addField("comment",comment);
                solrServer.add(solrInputDocument);
                solrServer.commit();

            } catch (Exception e) {
                e.printStackTrace();
                resultInfo= "该文件上传失败"+file.getOriginalFilename()+"<br/>";
            }

        }

        if (resultInfo==null||resultInfo.length()==0) {
            resultInfo="上传完毕";
        }
        return resultInfo;



    }

    @Override
    public List<ElecFileupload> queryAddData(String projId,String belongTo) {

        return datachartDao.queryAddData(projId,belongTo);
    }

    @Override
    public List<ElecFileupload> queryDatas(String projId, String belongTo,String queryString) {
        SolrQuery solrQuery=new SolrQuery();
//        datachartsearch:""+123+""
        if (StringUtils.isEmpty(queryString)) {
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("datachartsearch:\""+queryString+"\"");
        }

        if (!StringUtils.isEmpty(projId)) {
            solrQuery.addFilterQuery("ProjID:\""+projId+"\"");
        }
        if (!StringUtils.isEmpty(belongTo)) {
            solrQuery.addFilterQuery("BelongTo:\""+belongTo+"\"");
        }
        List<ElecFileupload> elecFileuploads=new ArrayList<>();
        try {
            QueryResponse query = solrServer.query(solrQuery);
            SolrDocumentList results = query.getResults();
            for (SolrDocument result : results) {
                ElecFileupload elecFileupload=new ElecFileupload();
                elecFileupload.setProjid(result.get("ProjID").toString());
                elecFileupload.setBelongto(result.get("BelongTo").toString());
                elecFileupload.setFilename(result.get("FileName").toString());
                elecFileupload.setFileurl(result.get("FileURL").toString());
                elecFileupload.setComment(result.get("comment").toString());
                elecFileuploads.add(elecFileupload);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return elecFileuploads;
    }

    private String genDirs(String projId, String belongTo){
        String dir1=UPLOAD_DIR_ROOT+"/"+projId;
        String dir2=dir1+"/"+belongTo+"/";

        File dir=new File(dir2);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir2;

    }
}
