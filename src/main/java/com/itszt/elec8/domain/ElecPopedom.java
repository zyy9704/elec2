package com.itszt.elec8.domain;

import java.io.Serializable;

public class ElecPopedom extends ElecPopedomKey implements Comparable<ElecPopedom>,Serializable {
    private String name;

    private String url;

    private String icon;

    private String target;

    private Boolean isparent;

    private Boolean ismenu;

    private Boolean ischecked;

    private String mid;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Boolean getIsparent() {
        return isparent;
    }

    public void setIsparent(Boolean isparent) {
        this.isparent = isparent;
    }

    public Boolean getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(Boolean ismenu) {
        this.ismenu = ismenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElecPopedom popedom = (ElecPopedom) o;

        return mid.equals(popedom.mid);
    }

    @Override
    public int hashCode() {
        return mid.hashCode();
    }

    @Override
    public int compareTo(ElecPopedom o) {
        return this.mid.compareTo(o.getMid());
    }
}