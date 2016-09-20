package com.aixforce.search;

import com.google.common.base.Objects;

public class SearchFacet extends Pair {
    private static final long serialVersionUID = 7637909699937211972L;
    private final Long count;

    public SearchFacet(Long id, Long count) {
        super(null,id);
        this.count = count;
    }

    public Long getCount(){
        return count;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null||!(obj instanceof SearchFacet)){
            return false;
        }
        SearchFacet that = (SearchFacet)obj;
        return Objects.equal(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",this.getId()).add("count",this.getCount()).toString();
    }
}
