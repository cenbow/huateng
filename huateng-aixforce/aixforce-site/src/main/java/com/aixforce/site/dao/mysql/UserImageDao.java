package com.aixforce.site.dao.mysql;

import com.aixforce.common.model.Paging;
import com.aixforce.site.model.UserImage;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-13
 */
@Repository
public class UserImageDao extends SqlSessionDaoSupport {
    public UserImage findById(Long id) {
        return getSqlSession().selectOne("UserImage.findById",id);
    }

    public Integer countOf(Long userId){
        return getSqlSession().selectOne("UserImage.countOf",userId);
    }

    public Paging<UserImage> findByUserId(Long userId,Integer offset,Integer pageSize){
        Integer count = countOf(userId);
        if(count == 0){
            return new Paging<UserImage>(0L, Collections.<UserImage>emptyList());
        }
        List<UserImage> userImages = getSqlSession().selectList("UserImage.findByUserId",
                ImmutableMap.of("userId",userId,"offset",offset,"limit",pageSize));
        return new Paging<UserImage>(count.longValue(),userImages);
    }


    public void create(UserImage userImage) {
        getSqlSession().insert("UserImage.create",userImage);
    }

    public void delete(Long id) {
        getSqlSession().delete("UserImage.delete",id);
    }

    public void deleteByUserId(Long userId){
        getSqlSession().delete("UserImage.deleteByUserId",userId);
    }

    public long totalSizeByUserId(Long userId){
        Long total = getSqlSession().selectOne("UserImage.totalSize",userId);
        return Objects.firstNonNull(total,0L);
    }
}
