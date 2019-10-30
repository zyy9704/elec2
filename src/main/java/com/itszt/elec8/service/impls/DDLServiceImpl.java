package com.itszt.elec8.service.impls;

import com.itszt.elec8.ConstantsKeys;
import com.itszt.elec8.dao.DDLDao;
import com.itszt.elec8.domain.ElecSystemddl;
import com.itszt.elec8.service.DDLService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zyy on 2019/8/8.
 */
@Service
public class DDLServiceImpl implements DDLService {
    @Resource
    private DDLDao ddlDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<String> queryAllKeywords() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS);
        List<String> keywords =null;
        if (o==null) {
            keywords = ddlDao.queryAllKeywords();
            if (keywords!=null) {
                valueOperations.set(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS,keywords);
            }
        }else {
            keywords= (List<String>) o;
        }
        return keywords;
    }

    @Override
    public List<ElecSystemddl> getKeywordDatas(String keyword) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS_DATAS+keyword);
        List<ElecSystemddl> elecSystemddls=null;
        if (o==null) {
            elecSystemddls = ddlDao.quaryDatasByKeyword(keyword);
            if (elecSystemddls!=null){
                valueOperations.set(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS_DATAS+keyword,elecSystemddls);
            }
        }else{
            elecSystemddls= (List<ElecSystemddl>) o;
        }

        return elecSystemddls;
    }

    @Override
    @Transactional
    public void addKeyword(String keyword, String[] datas) {
        for (int i = 0; i <datas.length; i++) {
            ddlDao.insertkeyword(keyword,i+1,datas[i]);
        }
        redisTemplate.delete(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS);

    }

    @Override
    @Transactional
    public void modifyKeyword(String keyword, String[] datas) {
        ddlDao.deletekeyword(keyword);
        for (int i = 0; i <datas.length; i++) {
            ddlDao.insertkeyword(keyword,i+1,datas[i]);
        }
        redisTemplate.delete(ConstantsKeys.REDIS_KEY_DDL_KEYWORDS_DATAS+keyword);


    }
}
