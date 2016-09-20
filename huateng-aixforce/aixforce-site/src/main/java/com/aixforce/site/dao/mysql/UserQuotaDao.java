package com.aixforce.site.dao.mysql;

import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.UserQuota;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-13
 */
@Repository
public class UserQuotaDao extends SqlSessionDaoSupport {

//    @Autowired
    private JedisTemplate template;

    @Autowired
    private UserImageDao userImageDao;

    public void create(UserQuota userQuota) {
        checkArgument(userQuota.getUserId() != null, "userId can not be null");
        getSqlSession().insert("UserQuota.create", userQuota);
    }

    public UserQuota findByUserId(Long userId) {
        return getSqlSession().selectOne("UserQuota.findByUserId", userId);
    }

    /**
     * 增加或者减少用户使用的图片消耗信息
     *
     * @param userId          用户id
     * @param imageCountDelta 图片数目变化,可以为负
     * @param imageSizeDelta  图片大小变化,可以为负
     */
    public void updateUsedImageInfo(final Long userId, final Integer imageCountDelta, final Integer imageSizeDelta) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(imageCountDelta != null, "imageCount delta can not be null");
        checkArgument(imageSizeDelta != null, "imageSize delta can not be null");
        getSqlSession().update("UserQuota.delta",
                ImmutableMap.of("userId", userId, "usedImageCountDelta", imageCountDelta, "usedImageSizeDelta", imageSizeDelta));
    }

    /**
     * 更新用户所使用的widget个数
     *
     * @param userId               用户id
     * @param usedWidgetCountDelta 变化数目个数,可正可负
     */
    public void updateUsedWidgetCount(Long userId, Integer usedWidgetCountDelta) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(usedWidgetCountDelta != null, "usedWidgetCountDelta delta can not be null");
        getSqlSession().update("UserQuota.delta", ImmutableMap.of("userId", userId, "usedWidgetCountDelta", usedWidgetCountDelta));
    }

    /**
     * 计算用户所消耗的widget个数
     *
     * @param userId 用户id
     */
    public void calculateWidgetCount(final Long userId) {
        Set<String> userSites = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(sites(userId));
            }
        });

        int count = 0;
        for (final String siteId : userSites) {
            String defaultSiteInstanceId = template.execute(new JedisTemplate.JedisAction<String>() {
                @Override
                public String action(Jedis jedis) {
                    return jedis.hget(site(Long.parseLong(siteId)),"defaultSiteInstanceId");
                }
            });
            if (Strings.isNullOrEmpty(defaultSiteInstanceId)) {
                continue;
            }
            final Long siteInstanceId = Long.parseLong(defaultSiteInstanceId);
            List<Integer> widgetCount = template.execute(new JedisTemplate.JedisAction<List<Integer>>() {
                @Override
                public List<Integer> action(Jedis jedis) {
                    List<Response<Long>> result = Lists.newArrayListWithCapacity(2);
                    Pipeline p = jedis.pipelined();
                    result.add(p.scard(headerWidgets(siteInstanceId)));
                    result.add(p.scard(footerWidgets(siteInstanceId)));
                    p.sync();
                    return Lists.transform(result,new Function<Response<Long>, Integer>() {
                        @Override
                        public Integer apply(Response<Long> input) {
                            return input.get().intValue();
                        }
                    });
                }
            });
            for (Integer i : widgetCount) {
                count+= i;
            }
            final Set<String> pageIds =template.execute(new JedisTemplate.JedisAction<Set<String>>() {
                @Override
                public Set<String> action(Jedis jedis) {
                    return jedis.smembers(pages(siteInstanceId));
                }
            });
            List<Integer> pageWidgetCounts = template.execute(new JedisTemplate.JedisAction<List<Integer>>() {
                @Override
                public List<Integer> action(Jedis jedis) {
                    List<Response<Long>> result = Lists.newArrayListWithCapacity(pageIds.size());
                    Pipeline p = jedis.pipelined();
                    for (String pageId : pageIds) {
                        result.add(p.scard(widgets(Long.parseLong(pageId))));
                    }
                    p.sync();
                    return Lists.transform(result,new Function<Response<Long>, Integer>() {
                        @Override
                        public Integer apply(Response<Long> input) {
                            return input.get().intValue();
                        }
                    });
                }
            });
            for (Integer i : pageWidgetCounts) {
                count+= i;
            }
        }
        UserQuota userQuota = new UserQuota();
        userQuota.setUserId(userId);
        userQuota.setUsedWidgetCount(count);
        getSqlSession().update("UserQuota.update", userQuota);
    }

    /**
     * 计算用户消耗的图片数目和大小
     *
     * @param userId 用户id
     */
    public void calculateUsedImageInfo(Long userId) {
        long total = userImageDao.totalSizeByUserId(userId);
        int count = userImageDao.countOf(userId);

        UserQuota userQuota = new UserQuota();
        userQuota.setUserId(userId);
        userQuota.setUsedImageCount(count);
        userQuota.setUsedImageSize(total);
        update(userQuota);
    }

    public void update(UserQuota userQuota) {
        getSqlSession().update("UserQuota.update", userQuota);
    }

    public void deleteByUserId(final Long userId) {
        getSqlSession().delete("UserQuota.deleteByUserId", userId);
    }
}
