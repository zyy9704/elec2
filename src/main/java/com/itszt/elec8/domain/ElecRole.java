package com.itszt.elec8.domain;

import java.io.Serializable;

public class ElecRole implements Serializable {
    private String roleid;

    private String rolename;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
}