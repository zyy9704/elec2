package com.itszt.elec8.service;

import com.itszt.elec8.domain.ElecFileupload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by zyy on 2019/8/17.
 */
public interface DatachartService {
    public String uploadFiles(MultipartFile[] uploads, String[] comments, String projId, String belongTo);

    public List<ElecFileupload> queryAddData(String projId, String belongTo);

    public List<ElecFileupload> queryDatas(String projId, String belongTo,String queryString);
}
