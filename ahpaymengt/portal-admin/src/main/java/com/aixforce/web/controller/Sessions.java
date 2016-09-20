/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.exception.PasswordIncorrectException;
import com.aixforce.user.base.exception.UserLockedException;
import com.aixforce.user.base.exception.UserNotExistException;
import com.aixforce.user.model.User;
import com.aixforce.user.service.AccountService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-06
 */
@Controller
@RequestMapping("/users")
public class Sessions {
    private final static Logger log = LoggerFactory.getLogger(Sessions.class);

    private final static Pattern pattern = Pattern.compile("/admin[^/]*/");


    private final AccountService accountService;


    @Autowired
    public Sessions(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String show() {
        return "admin/users/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpServletRequest request) throws IOException {
        try {

            User user = accountService.login(email, password);
            BaseUser baseUser = new BaseUser(user.getId(),user.getEmail(),BaseUser.TYPE.fromNumber(user.getType()));
            request.getSession().setAttribute("ADMIN", baseUser);
            String redirectUrl = "/users";
            String previousUrl = (String) request.getSession().getAttribute(CommonConstants.PREVIOUS_URL);
            if (!Strings.isNullOrEmpty(previousUrl)) {
                redirectUrl = previousUrl;
            }

            Matcher matcher = pattern.matcher(redirectUrl);
            redirectUrl = matcher.replaceAll("/");
            return "redirect:" + redirectUrl;

        } catch (UserNotExistException e) {
            return errorBack();
        } catch (PasswordIncorrectException e) {
            throw new JsonResponseException(500, "user.password.incorrect");
        } catch (UserLockedException e) {
            throw new JsonResponseException(500, "user.account.locked");
        } catch (Exception e) {
            log.error("failed to login user(email={}),cause:{}", email, e);
            throw new JsonResponseException(500, "user.login.fail");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().invalidate();
            return "redirect:/components";
        } catch (Exception e) {
            log.error("failed to logout user,cause:", e);
            throw new JsonResponseException(500, "user.logout.fail");
        }
    }

    private String errorBack() {
        throw new JsonResponseException(500, "user.not.exits");
    }
}
