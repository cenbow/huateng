package cn.com.huateng.mall.mock;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.user.model.User;
import com.aixforce.user.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-21
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public Paging<User> list(@ParamInfo("status") Integer status, @ParamInfo("pageNo") Integer pageNo, @ParamInfo("count") Integer count) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User findUserById(@ParamInfo("id") Long id) {
        User mockUser = new User();
        mockUser.setName("admin");
        return mockUser;
    }

    @Override
    public User findUserByName(@ParamInfo("name") String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long createUser(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resetPassword(Long id, String encryptPassword) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> refreshToken(String email, String token) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteUser(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Nullable
    @Override
    public User findUserByEmail(@ParamInfo("email") String email) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User findUserByMobile(@ParamInfo("mobile") String mobile) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User login(String email, String password) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User loginByMobile(String mobile, String password) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void changeMobile(Long userId, String mobile, String password) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
