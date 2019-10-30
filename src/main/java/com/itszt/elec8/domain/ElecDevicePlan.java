package com.itszt.elec8.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ElecDevicePlan {
    private String devplanid;

    private String jctid;

    private String devname;

    private String devtype;

    private String trademark;

    private String spectype;

    private String producehome;

    private String producearea;

    private String useness;

    private String quality;

    private String useunit;

    private BigDecimal devexpense;

    private Date plandate;

    private String adjustperiod;

    private String overhaulperiod;

    private String configure;

    private String comment;

    private String purchasestate;

    private String isdelete;

    private String createempid;

    private Date createdate;

    private String lastempid;

    private Date lastdate;

    private String qunit;

    private String apunit;

    private String opunit;

    public String getDevplanid() {
        return devplanid;
    }

    public void setDevplanid(String devplanid) {
        this.devplanid = devplanid == null ? null : devplanid.trim();
    }

    public String getJctid() {
        return jctid;
    }

    public void setJctid(String jctid) {
        this.jctid = jctid == null ? null : jctid.trim();
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname == null ? null : devname.trim();
    }

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype == null ? null : devtype.trim();
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark == null ? null : trademark.trim();
    }

    public String getSpectype() {
        return spectype;
    }

    public void setSpectype(String spectype) {
        this.spectype = spectype == null ? null : spectype.trim();
    }

    public String getProducehome() {
        return producehome;
    }

    public void setProducehome(String producehome) {
        this.producehome = producehome == null ? null : producehome.trim();
    }

    public String getProducearea() {
        return producearea;
    }

    public void setProducearea(String producearea) {
        this.producearea = producearea == null ? null : producearea.trim();
    }

    public String getUseness() {
        return useness;
    }

    public void setUseness(String useness) {
        this.useness = useness == null ? null : useness.trim();
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality == null ? null : quality.trim();
    }

    public String getUseunit() {
        return useunit;
    }

    public void setUseunit(String useunit) {
        this.useunit = useunit == null ? null : useunit.trim();
    }

    public BigDecimal getDevexpense() {
        return devexpense;
    }

    public void setDevexpense(BigDecimal devexpense) {
        this.devexpense = devexpense;
    }

    public Date getPlandate() {
        return plandate;
    }

    public void setPlandate(Date plandate) {
        this.plandate = plandate;
    }

    public String getAdjustperiod() {
        return adjustperiod;
    }

    public void setAdjustperiod(String adjustperiod) {
        this.adjustperiod = adjustperiod == null ? null : adjustperiod.trim();
    }

    public String getOverhaulperiod() {
        return overhaulperiod;
    }

    public void setOverhaulperiod(String overhaulperiod) {
        this.overhaulperiod = overhaulperiod == null ? null : overhaulperiod.trim();
    }

    public String getConfigure() {
        return configure;
    }

    public void setConfigure(String configure) {
        this.configure = configure == null ? null : configure.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getPurchasestate() {
        return purchasestate;
    }

    public void setPurchasestate(String purchasestate) {
        this.purchasestate = purchasestate == null ? null : purchasestate.trim();
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }

    public String getCreateempid() {
        return createempid;
    }

    public void setCreateempid(String createempid) {
        this.createempid = createempid == null ? null : createempid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastempid() {
        return lastempid;
    }

    public void setLastempid(String lastempid) {
        this.lastempid = lastempid == null ? null : lastempid.trim();
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public String getQunit() {
        return qunit;
    }

    public void setQunit(String qunit) {
        this.qunit = qunit == null ? null : qunit.trim();
    }

    public String getApunit() {
        return apunit;
    }

    public void setApunit(String apunit) {
        this.apunit = apunit == null ? null : apunit.trim();
    }

    public String getOpunit() {
        return opunit;
    }

    public void setOpunit(String opunit) {
        this.opunit = opunit == null ? null : opunit.trim();
    }
}