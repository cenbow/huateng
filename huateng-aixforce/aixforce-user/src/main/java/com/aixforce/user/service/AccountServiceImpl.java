/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.user.service;

import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.NameValidator;
import com.aixforce.exception.ServiceException;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.exception.*;
import com.aixforce.user.model.User;
import com.aixforce.user.mysql.UserDao;
import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * 安全相关实体的管理类,包括用户和权限组.
 *
 * @author jlchen
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService{

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final static HashFunction sha512 = Hashing.sha512();

    private final static Splitter splitter = Splitter.on('@').trimResults();

    private final static Joiner joiner = Joiner.on('@').skipNulls();

    private final static HashFunction md5 = Hashing.md5();

    private final LoadingCache<Long,User> userCache;

    @Autowired
    private Validator validator;

    @Autowired
    private UserDao userDao;


    public AccountServiceImpl() {
        userCache = CacheBuilder.newBuilder().expireAfterWrite(15,TimeUnit.MINUTES).build(new CacheLoader<Long, User>() {
            @Override
            public User load(Long id) throws Exception {
                return userDao.findById(id);
            }
        });
    }

    /**
     * 从数据库中load用户,按照id逆序排列
     *
     * @param status 用户状态
     * @param pageNo 页码
     * @param count  每页返回条数
     * @return 分页结果
     */
    @Override
    public Paging<User> list(Integer status, Integer pageNo, Integer count) {
        pageNo = firstNonNull(pageNo, 1);
        count = firstNonNull(count, 20);
        pageNo = pageNo > 0 ? pageNo : 1;
        count = count > 0 ? count : 20;
        int offset = (pageNo - 1) * count;
        return userDao.findUsers(status, offset, count);
    }

    /**
     * user related CRUD begins *
     */
    @Override
    @Nullable
    public User findUserById(Long id) {
        checkArgument(id != null, "id can not be null");
        return userCache.getUnchecked(id);
    }

    @Override
    public User findUserByName(String name) {
        checkArgument(!Strings.isNullOrEmpty(name), "name can not be empty");
        return userDao.findByName(name);
    }


    //@Transactional(readOnly = false)
    @Override
    public Long createUser(User user) {
        checkArgument(user.getId() == null, "not a new user object");
        checkArgument(!Strings.isNullOrEmpty(user.getName()), "user name can not be null");
        try {
            if (Strings.isNullOrEmpty(user.getName())) {
                throw new IllegalArgumentException("用户名称不能为空");
            }
            if (!NameValidator.validate(user.getName())) {
                throw new IllegalArgumentException("用户名称只能由字母,数字和下划线组成");
            }
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {

                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<?> violation : violations) {
                    sb.append(violation.getMessage()).append("\n");
                }
                throw new IllegalArgumentException(sb.toString());

            }
            User existed = userDao.findByEmail(user.getEmail());
            if (existed != null) {
                logger.error("failed to create user {},cause: duplicated email ", user);
                throw new DuplicatedEmailException("user.email.duplicated");
            }

            existed = userDao.findByName(user.getName());
            if (existed != null) {
                logger.error("failed to create user {},cause: duplicated name ", user);
                throw new DuplicatedNameException("user.name.duplicated");
            }
            user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));//encrypted password
            userDao.create(user);
            return user.getId();
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, DuplicatedEmailException.class);
            Throwables.propagateIfInstanceOf(e, DuplicatedNameException.class);
            logger.error("failed to create new user {}, cause:{} ", user, e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        checkArgument(user.getId() != null, "user id can not be null when updated");
        if (user.getEncryptedPassword() != null) {
            user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));//encrypted password
        }
        try {
            if (!NameValidator.validate(user.getName())) {
                throw new IllegalArgumentException("用户名称只能由字母,数字和下划线组成");
            }

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {

                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<?> violation : violations) {
                    sb.append(violation.getMessage()).append("\n");
                }
                throw new IllegalArgumentException(sb.toString());

            }
            userDao.update(user);
            userCache.invalidate(user.getId());
        } catch (Exception e) {
            logger.error("failed to updated user {},cause:{}", user, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String email, String password) {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotExistException();
        }
        if (Objects.equal(User.STATUS.LOCKED.toNumber(), user.getStatus())) {
            throw new UserLockedException();
        }
        String storedPassword = user.getEncryptedPassword();


        if (passwordMatch(password, storedPassword)) {
            return user;
        } else {
            throw new PasswordIncorrectException();
        }
    }

    private boolean passwordMatch(String password, String encryptedPassword) {
        Iterable<String> parts = splitter.split(encryptedPassword);
        String salt = Iterables.get(parts, 0);
        String realPassword = Iterables.get(parts, 1);
        return Objects.equal(sha512. hashUnencodedChars(password + salt).toString().substring(0, 20), realPassword);
    }

    private String encryptPassword(String password) {
        String salt = md5.newHasher().putUnencodedChars(UUID.randomUUID().toString()).putLong(System.currentTimeMillis()).hash()
                .toString().substring(0, 4);
        String realPassword = sha512.hashUnencodedChars(password + salt).toString().substring(0, 20);
        return joiner.join(salt, realPassword);
    }

    /**
     * ddbao支持手机号码登陆
     *
     * @param mobile   手机号码
     * @param password 密码
     * @return 用户
     */
    @Override
    public User loginByMobile(String mobile, String password) {
        User user = findUserByMobile(mobile);
        if (user == null) {
            throw new UserNotExistException();
        }
        if (Objects.equal(User.STATUS.LOCKED.toNumber(), user.getStatus())) {
            throw new UserLockedException();
        }
        String storedPassword = user.getEncryptedPassword();


        if (passwordMatch(password, storedPassword)) {
            return user;
        } else {
            throw new PasswordIncorrectException();
        }
    }

    @Override
    public void changeMobile(Long userId, String mobile, String password) {
        checkArgument(userId != null, "id can not be null");
        checkArgument(!Strings.isNullOrEmpty(mobile), "mobile can not be null");
        checkArgument(!Strings.isNullOrEmpty(password), "password can not be null");
        User user = userDao.findById(userId);
        if (user == null) {
            logger.error("can not find user whose id={}", userId);
            throw new UserNotExistException();
        }
        if (!passwordMatch(password, user.getEncryptedPassword())) {
            logger.error("password not match for user(id={})", userId);
            throw new PasswordIncorrectException();
        }
        try {
            User updated = new User();
            updated.setId(userId);
            updated.setMobile(mobile);
            userDao.update(updated);
            userCache.invalidate(userId);
        } catch (Exception e) {
            logger.error("failed to change mobile for user(id={}),cause:{}",userId,e.getMessage());
            throw new ServiceException(e.getMessage(),e);
        }
    }


    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        checkArgument(userId != null, "id can not be null");
        checkArgument(oldPassword != null, "oldPassword can not be null");
        checkArgument(newPassword != null, "newPassword can not be null");
        User user = userDao.findById(userId);
        if (user == null) {
            logger.error("can not find user whose id={}", userId);
            throw new UserNotExistException();
        }
        if (!passwordMatch(oldPassword, user.getEncryptedPassword())) {
            logger.error("password not match for user(id={})", userId);
            throw new PasswordIncorrectException();
        }
        User updated = new User();
        updated.setId(userId);
        updated.setEncryptedPassword(encryptPassword(newPassword));
        try {
            userDao.update(updated);
            userCache.invalidate(userId);
        } catch (Exception e) {
            logger.error("failed to change password for user (id={}),cause:{}", userId, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> refreshToken(String email, String token) {
        //no need to update user session now
        return ImmutableMap.of(email,token);
    }


    @Override
    public void resetPassword(Long id, String password) {
        checkArgument(id != null, "od can not be null");
        checkArgument(password != null, "password can not be null");


        User user = findUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("user not exists");
        }

        User updated = new User();
        updated.setId(user.getId());
        updated.setEncryptedPassword(encryptPassword(password));
        try {
            userDao.update(updated);
            userCache.invalidate(id);
        } catch (Exception e) {
            logger.error("failed to change password for user (id={}),cause:{}", user.getId(), e);
            throw new ServiceException(e);
        }
        userCache.invalidate(id);
    }


    /**
     * 删除用户,如果尝试删除超级管理员将抛出异常.
     */
    @Override
    public void deleteUser(Long id) {
        if (isSupervisor(id)) {
            throw new ServiceException("不能删除超级管理员用户");
        }
        try {
            userDao.delete(id);
            userCache.invalidate(id);
        } catch (Exception e) {
            logger.error("failed to delete user(id={})", id);
            throw new ServiceException(e);
        }
    }

    /**
     * 判断是否超级管理员.
     */
    private boolean isSupervisor(Long id) {
        User user = findUserById(id);
        return user != null && user.getTypeEnum() == BaseUser.TYPE.ADMIN;
    }

    @Override
    @Nullable
    public User findUserByEmail(String email) {
        try {
            return userDao.findByEmail(email);
        } catch (Exception e) {
            logger.error("failed to find user by email {},cause:{}", email, e);
            throw new ServiceException(e);
        }
    }

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return 用户
     */
    @Override
    public User findUserByMobile(String mobile) {
        try {
            return userDao.findByMobile(mobile);
        } catch (Exception e) {
            logger.error("failed to find user by mobile {},cause:{}", mobile, e);
            throw new ServiceException(e);
        }
    }
}
