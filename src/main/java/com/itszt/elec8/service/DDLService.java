package com.itszt.elec8.service;

import com.itszt.elec8.domain.ElecSystemddl;

import java.util.List;

/**
 * Created by zyy on 2019/8/8.
 */
public interface DDLService {
    public List<String> queryAllKeywords();

    public List<ElecSystemddl> getKeywordDatas(String keyword);

    public void addKeyword(String keyword,String[] datas) throws Exception;

    public void modifyKeyword(String keyword,String[] datas) throws Exception;
}
