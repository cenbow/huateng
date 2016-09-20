package com.aixforce.site.model.redis;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
 * Author: jlchen
 * Date: 2013-01-10
 */
public class SiteProfile implements Serializable {

    private static final long serialVersionUID = -6314277264494304442L;

    @Getter
    @Setter
    private Long siteId;

    @Getter
    @Setter
    private Long todayPv;//不再数据库中存储,从site:[id]:todayPv中获取

    @Getter
    @Setter
    private Long totalPv;

    @Override
    public int hashCode() {
        return Objects.hashCode(siteId);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof SiteProfile)){
            return false;
        }
        SiteProfile that = (SiteProfile)o;
        return Objects.equal(siteId,that.siteId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("siteId",siteId).add("totalPv",totalPv).add("todayPv",todayPv).toString();
    }
}
