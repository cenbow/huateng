package com.aixforce.item.dao.mysql;

import com.aixforce.common.model.Paging;
import com.aixforce.item.model.Item;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-01-31
 */
@Repository
public class ItemDao extends SqlSessionDaoSupport {

    public Item findById(Long id) {
        return getSqlSession().selectOne("Item.findById", id);
    }

    public List<Item> findByIds(List<Long> ids){
        return getSqlSession().selectList("Item.findByIds", ids);
    }

    public void create(Item item) {
        getSqlSession().insert("Item.create", item);
    }

    public void delete(Long id) {
        getSqlSession().delete("Item.delete", id);
    }

    public void update(Item item) {
        getSqlSession().update("Item.update", item);
    }

    public void bulkUpdateStatus(Long userId,int status,List<Long> ids){
        Map<String,Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("status",status);
        params.put("userId",userId);
        params.put("ids",ids);
        switch (Item.Status.fromNumber(status)){
            case ON_SHELF:
                params.put("onShelf",true);
                break;
            case OFF_SHELF:
                params.put("offShelf",true);
                break;
            case FROZEN:
                params.put("frozen",true);
                break;
            case DELETED:
                params.put("deleted",true);
                break;
            default:
                throw new IllegalArgumentException("unknown item status value:"+status);
        }
        getSqlSession().update("Item.bulkUpdateStatus",params);
    }

    /**
     * 卖家后台管理商品列表
     * @param userId 卖家id
     * @param offset  偏移
     * @param size    条数
     * @param params  查询参数
     * @return 商品列表
     */
    public Paging<Item> sellerItems(Long userId, int offset, int size, Map<String, Object> params) {
        params.put("userId",checkNotNull(userId));
        Integer count = getSqlSession().selectOne("Item.sellerItemCount",params);
        count = Objects.firstNonNull(count,0);
        if(count == 0){
            return new Paging<Item>(0L, Collections.<Item>emptyList());
        }

        params.put("offset",offset);
        params.put("limit",size);

        List<Item> items = getSqlSession().selectList("Item.sellerItems", params);
        return new Paging<Item>(count.longValue(),items);
    }

    public List<Item> forDump(Long lastId, Integer limit){
        return getSqlSession().selectList("Item.forDump", ImmutableMap.of("lastId", lastId, "limit", limit));
    }

    public List<Item> forDeltaDump(Long lastId,String compared,Integer limit){
        return getSqlSession().selectList("Item.forDeltaDump",ImmutableMap.of("lastId",lastId,"limit",limit,"compared", compared));
    }

    public Long maxId(){
        return Objects.firstNonNull((Long) getSqlSession().selectOne("Item.maxId"), 0L);
    }

    public void changeStock(Long id, int delta) {
        getSqlSession().update("Item.changeStock", ImmutableMap.of("id",id,"delta",delta));
    }
}
