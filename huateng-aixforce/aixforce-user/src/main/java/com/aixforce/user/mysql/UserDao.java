package com.aixforce.user.mysql;

import com.aixforce.common.model.Paging;
import com.aixforce.user.model.User;
import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-12
 */
@Repository
public class UserDao extends SqlSessionDaoSupport {
    public User findById(Long id) {
        return  getSqlSession().selectOne("User.findById", id);
    }

    public User findByEmail(String email) {
        return  getSqlSession().selectOne("User.findByEmail", email);
    }

    public void create(User user) {
        getSqlSession().insert("User.create",user);
    }

    public int delete(Long id) {
        return getSqlSession().delete("User.delete",id);
    }

    public boolean update(User user){
        return  getSqlSession().update("User.update",user)==1;
    }

    public Paging<User> findUsers(Integer status,Integer offset,Integer limit){
        Long total = getSqlSession().selectOne("User.count",status);
        List<User> users = getSqlSession().selectList("User.pagination",
                ImmutableMap.of("status",status,"offset",offset,"limit",limit));
        return new Paging<User>(total,users);
    }

    public User findByName(String name) {
        return getSqlSession().selectOne("User.findByName",name);
    }

    public User findByThirdPartTypeAndId(User.ThirdPartType thirdPartType, String thirdPartId) {
        return getSqlSession().selectOne("User.findByThirdPartTypeAndId",
                ImmutableMap.of("thirdPartType",thirdPartType.value(),"thirdPartId",thirdPartId));
    }

    public User findByMobile(String mobile) {
        return getSqlSession().selectOne("User.findByMobile",mobile);
    }
}
