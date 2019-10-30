package com.itszt.elec8.controller.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by zyy on 2019/8/23.
 */
public class AddSiteInfoVo {
    private String jctId;
    private String stationName;
    private String stationCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date useStartDate;
    private String jcfrequency;
    private String produceHome;
    private String contactType;
    private String stationType;
    private String attributionGround;
    private String comment;

    public String getJctId() {
        return jctId;
    }

    public void setJctId(String jctId) {
        this.jctId = jctId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Date getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(Date useStartDate) {
        this.useStartDate = useStartDate;
    }

    public String getJcfrequency() {
        return jcfrequency;
    }

    public void setJcfrequency(String jcfrequency) {
        this.jcfrequency = jcfrequency;
    }

    public String getProduceHome() {
        return produceHome;
    }

    public void setProduceHome(String produceHome) {
        this.produceHome = produceHome;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getAttributionGround() {
        return attributionGround;
    }

    public void setAttributionGround(String attributionGround) {
        this.attributionGround = attributionGround;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
