package com.itszt.elec8.service;

import com.itszt.elec8.domain.ElecUser;

/**
 * Created by zyy on 2019/8/7.
 */
public interface UserService {

    public ElecUser login(String name, String password);
}
