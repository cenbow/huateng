package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.UserService;

import com.aixforce.user.base.BaseUser;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-25
 */
@Component
public class UserCache {
    private final static Logger log = LoggerFactory.getLogger(UserCache.class);

    @Value("#{app.adminAccount}")
    private String adminAccount;

    private final LoadingCache<Long, CommonUser> cache;

    @Autowired
    public UserCache(final UserService userService) {
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES)
                .maximumSize(100000).build(new CacheLoader<Long, CommonUser>() {
                    @Override
                    public CommonUser load(Long userId) throws Exception {
                        if (userId > 0) {
                            Response<TInfoCustomer> result = userService.findUserByPK(userId.toString());
                            if (result.isSuccess()) {
                                TInfoCustomer user = result.getResult();
                                BaseUser.TYPE type = BaseUser.TYPE.BUYER;
                                Iterable<String> parts;
                                if (adminAccount.indexOf(",") > 0) {
                                    parts = Splitter.on(",").trimResults().split(adminAccount);
                                    for (String str : parts) {
                                        if (Objects.equal(str, user.getUnifyId())) {
                                            type = BaseUser.TYPE.ADMIN;
                                            break;
                                        }
                                    }
                                } else {
                                    if (Objects.equal(adminAccount, user.getUnifyId())) {
                                        type = BaseUser.TYPE.ADMIN;
                                    }
                                }
                                String lt="";
                                if(user.getLastSuccessLoginTime()!=null){
                                    lt= user.getLastSuccessLoginTime().toLocaleString();
                                }

                                return new CommonUser(Long.parseLong(user.getCustomerNo()), user.getName(),
                                        type, user.getUnifyId(),lt,user.getMobileNo());
                            } else {
                                log.error("failed to find user whose id={}", userId);
                                throw new RuntimeException("failed to find user by id");
                            }
                        } else { //卡号
                            //id和name都设置为卡号
                            Long id = -userId; //恢复为正值
                            return new CommonUser(id, id.toString(), BaseUser.TYPE.OTHER, null, null);
                        }
                    }
                });
    }

    public CommonUser getByUserId(Long userId) {
        return cache.getUnchecked(userId);
    }

    public void evict(Long userId) {
        cache.invalidate(userId);
    }

}

