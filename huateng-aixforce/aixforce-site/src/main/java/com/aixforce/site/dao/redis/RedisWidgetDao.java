/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.Widget;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/13/12 2:19 PM
 */
@Repository
public class RedisWidgetDao extends RedisBaseDao<Widget> {
    @Autowired
    public RedisWidgetDao(JedisTemplate template) {
        super(template);
    }

    public Widget findById(Long widgetId) {
        Widget widget = findByKey(widgetId);
        return widget.getId() != null ? widget : null;
    }

    public List<Widget> siteLevelWidgets(final Widget.BelongType belongType, final Long siteInstanceId) {
        String key;
        switch (belongType) {
            case HEADER:
                key = headerWidgets(siteInstanceId);
                break;
            case FOOTER:
                key = footerWidgets(siteInstanceId);
                break;
            default:
                throw new IllegalArgumentException("unknown belong type: " + belongType);
        }
        final String realKey = key;
        final Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(realKey);
            }
        });
        return super.findByIds(ids);
    }

    public List<Widget> pageWidgets(final Long pageId) {
        final Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(widgets(pageId));
            }
        });
        return super.findByIds(ids);
    }

    /**
     * get item describe widgets  by item id
     *
     * @param itemId item's id
     * @return the description of item
     */
    public List<Widget> itemDescWidgets(final Long itemId) {
        final Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(itemWidgets(itemId));
            }
        });
        return super.findByIds(ids);
    }

    /**
     * 删除耽搁widget
     *
     * @param widgetId widget id
     */
    public void delete(final Long widgetId) {
        Widget widget = findById(widgetId);
        if (widget == null) {
            return;
        }
        final Widget.BelongType belongType = Widget.BelongType.fromNumber(widget.getBelongType());
        final Long belongId = widget.getBelongId();
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(Widget.class,widgetId));

                switch (belongType){
                    case HEADER:
                        t.srem(headerWidgets(belongId), widgetId.toString());
                        break;
                    case FOOTER:
                        t.srem(footerWidgets(belongId), widgetId.toString());
                        break;
                    case PAGE:
                        t.srem(widgets(belongId),widgetId.toString());
                        break;
                    case ITEM:
                        t.srem(itemWidgets(belongId),widgetId.toString());
                        break;
                    default:
                        throw new IllegalArgumentException("unknown belong type: " + belongType);
                }
                t.exec();
            }
        });
    }

    /**
     * 批量删除widget操作,注意,这些widget应该是属于同一个BelongType的
     *
     * @param ids 待删除的widget id 列表
     */
    public void batchDelete(final List<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }
        final Widget widget = findById(ids.get(0));
        final String[] keys =Iterables.toArray(Iterables.transform(ids, new Function<Long, String>() {
            @Override
            public String apply(Long id) {
                return id.toString();
            }
        }), String.class);
        final Widget.BelongType belongType = Widget.BelongType.fromNumber(widget.getBelongType());
        final Long belongId = widget.getBelongId();
        String widgetIndex;
        switch (belongType){
            case HEADER:
                widgetIndex = headerWidgets(belongId);
                break;
            case FOOTER:
                widgetIndex = footerWidgets(belongId);
                break;
            case PAGE:
                widgetIndex = widgets(belongId);
                break;
            case ITEM:
                widgetIndex = itemWidgets(belongId);
                break;
            default:
                throw new IllegalArgumentException("unknown belong type: " + belongType);
        }
        final String realIndex = widgetIndex;
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(keys);
                //remove from index
                for (String key : keys) {
                    t.srem(realIndex,key);
                }
                t.exec();
            }
        });
    }

    /**
     * 处理item detail 的所有widget,注意,这里不处理behavior和jsonData字段
     *
     * @param itemId 商品id
     * @param widgets detail的所有widgets
     */
    public void batchUpdateDetailWidgets(final Long itemId, final List<Widget> widgets) {
        final Set<String > originalIds = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(itemWidgets(itemId));
            }
        });
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(itemWidgets(itemId));
                for (Widget widget : widgets) {
                    String id = widget.getId().toString();
                    if(originalIds.remove(id)){
                        saveWidget(t,widget);
                    }else { //unknown widget id, may be hacked
                        t.discard();
                        throw new IllegalArgumentException("widget(id=" + id + ") is not belong to item(id=" + itemId + ")");
                    }
                }
                //remove orphan widget of this site instance if exists
                if (!originalIds.isEmpty()) {
                    for (String originalId : originalIds) {
                        t.del(entityId(Widget.class, originalId));
                    }
                }
                t.exec();
            }
        });
    }


    /**
     * 处理站点整个页面(包括页头,页尾,body部分)的所有widget,注意,这里不处理behavior和jsonData字段
     *
     * @param siteInstanceId 站点实例id
     * @param pageId         页面id
     * @param widgets        页面所有的widget
     */
    public void batchUpdate(final Long siteInstanceId, final Long pageId, final List<Widget> widgets) {
        final Set<String> originIds = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                Set<String> all = Sets.newHashSet();
                Pipeline p = jedis.pipelined();
                Response<Set<String>> headerWidgetIds = p.smembers(headerWidgets(siteInstanceId));
                Response<Set<String>> footerWidgetIds = p.smembers(footerWidgets(siteInstanceId));
                Response<Set<String>> pageWidgetIds = p.smembers(widgets(pageId));
                p.sync();
                all.addAll(headerWidgetIds.get());
                all.addAll(footerWidgetIds.get());
                all.addAll(pageWidgetIds.get());
                return all;
            }
        });

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                //remove old index first
                t.del(headerWidgets(siteInstanceId),footerWidgets(siteInstanceId),widgets(pageId));
                for (Widget widget : widgets) {
                    String id = widget.getId().toString();
                    if (originIds.remove(id)) {
                        saveWidget(t, widget);
                    } else {
                        //unknown widget id, may be hacked
                        t.discard();
                        throw new IllegalArgumentException("widget(id=" + id + ") is not belong to site instance(id=" + siteInstanceId + ")");
                    }
                }
                //remove orphan widget of this site instance if exists
                if (!originIds.isEmpty()) {
                    for (String originId : originIds) {
                        t.del(rawKey(entityId(Widget.class, Long.parseLong(originId))));
                    }
                }
                t.exec();
            }
        });
    }

    //save or update widget and add to correspond index
    void saveWidget(Transaction t, Widget widget) {
        Long id = widget.getId();
        t.hmset(entityId(Widget.class,id),stringHashMapper.toHash(widget));
        // add to correspond index
        switch (Widget.BelongType.fromNumber(widget.getBelongType())) {
            case HEADER:
                t.sadd(headerWidgets(widget.getBelongId()),id.toString());
                break;
            case FOOTER:
                t.sadd(footerWidgets(widget.getBelongId()),id.toString());
                break;
            case PAGE:
                t.sadd(widgets(widget.getBelongId()),id.toString());
                break;
            case ITEM:
                t.sadd(itemWidgets(widget.getBelongId()),id.toString());
                break;
            default:
                t.discard();
                throw new IllegalArgumentException("unknown belong type: " + widget.getBelongType());

        }
    }

    public void updateBehavior(final Long id, final String behavior) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hset(entityId(Widget.class,id),"behavior",behavior);
            }
        });
    }

    public void updateJsonData(final Long id, final String jsonData) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hset(entityId(Widget.class,id),"jsonData",jsonData);
            }
        });
    }

    //避免出错,分成两个接口
    public void create(Long id, final Widget widget) {
        widget.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                saveWidget(t,widget);
                t.exec();
            }
        });
    }

    public void create(Widget widget) {
        Long id = newId();
        create(id, widget);
    }
}
