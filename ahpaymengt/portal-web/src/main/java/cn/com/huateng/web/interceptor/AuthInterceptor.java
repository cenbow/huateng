package cn.com.huateng.web.interceptor;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import com.google.common.net.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkState;

/*
 * Author: jlchen
 * Date: 2013-01-23
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final CommonConstants commonConstants;

    private final static Pattern rewritePattern = Pattern.compile("/sites/[^/]+/");

    private final Set<WhiteItem> whiteList;

    private final Set<AuthItem> protectList;


    public AuthInterceptor(CommonConstants commonConstants) throws Exception {
        this.commonConstants = commonConstants;
        protectList = Sets.newHashSet();
        Resources.readLines(Resources.getResource("/protected_list"), Charsets.UTF_8, new LineProcessor<Void>() {
            @Override
            public boolean processLine(String line) throws IOException {
                if (!Strings.isNullOrEmpty(line)) {
                    Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                    checkState(Iterables.size(parts) == 2, "illegal protected_list configuration [%s]", line);
                    Pattern urlPattern = Pattern.compile("^"+Iterables.get(parts, 0)+"$");
                    String rolesPart = Iterables.get(parts, 1);
                    ImmutableSet.Builder<BaseUser.TYPE> roles = ImmutableSet.builder();
                    for (String role : Splitter.on(',').omitEmptyStrings().trimResults().split(rolesPart)) {
                        if (Objects.equal("ALL", role.toUpperCase())) {
                            for (BaseUser.TYPE type : BaseUser.TYPE.values()) {
                                roles.add(type);
                            }
                        } else {
                            roles.add(BaseUser.TYPE.fromName(role));
                        }
                    }
                    protectList.add(new AuthItem(urlPattern, roles.build()));

                }
                return true;
            }

            @Override
            public Void getResult() {
                return null;
            }
        });

        whiteList = Sets.newHashSet();
        Resources.readLines(Resources.getResource("/white_list"), Charsets.UTF_8, new LineProcessor<Void>() {
            @Override
            public boolean processLine(String line) throws IOException {
                if (!Strings.isNullOrEmpty(line)) {
                    Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                    checkState(Iterables.size(parts) == 2, "illegal white_list configuration [%s]", line);


                    Pattern urlPattern = Pattern.compile("^"+Iterables.get(parts, 0)+"$");
                    String methods = Iterables.get(parts, 1).toLowerCase();
                    ImmutableSet.Builder<String> httpMethods = ImmutableSet.builder();
                    for (String method : Splitter.on(',').omitEmptyStrings().trimResults().split(methods)) {
                        httpMethods.add(method);
                    }
                    whiteList.add(new WhiteItem(urlPattern, httpMethods.build()));

                }
                return true;
            }

            @Override
            public Void getResult() {
                return null;
            }
        });
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        Matcher matcher = rewritePattern.matcher(requestURI);
        requestURI = matcher.replaceAll("/");
        BaseUser user = UserUtil.getCurrentUser();

        String method = request.getMethod().toLowerCase();
        for (WhiteItem whiteItem : whiteList) {  //method and uri matches with white list, ok
            if (whiteItem.httpMethods.contains(method) && whiteItem.pattern.matcher(requestURI).matches()) {
                return true;
            }
        }

        boolean inProtectedList = false;
        for (AuthItem authItem : protectList) { //protected url should be authorized
            if (authItem.pattern.matcher(requestURI).matches()) {
                inProtectedList = true;
                if (user != null) {    //用户已登陆
                    if (authItem.roles.contains(user.getTypeEnum())) { //用户角色匹配
                        return true;
                    }
                } else { //用户未登陆
                    redirectToLogin(request, response);
                    return false;
                }
            }
        }

        if(inProtectedList){
            //用户角色无此权限，统一跳转说明页
            redirectToExplain(request, response);
        }

        if (!Objects.equal(method, "get") && user == null) { //write operation need login
            redirectToLogin(request, response);
            return false;
        }
        return true;
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            throw new JsonResponseException(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
        }
        String currentUrl = request.getRequestURL().toString();

        if (!Strings.isNullOrEmpty(request.getQueryString())) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        Matcher matcher = rewritePattern.matcher(currentUrl);
        currentUrl = matcher.replaceAll("/");

        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("http://" + commonConstants.getMainSite() + "/login?target={target}").build();
        URI uri = uriComponents.expand(currentUrl).encode().toUri();
        response.sendRedirect(uri.toString());
    }

    private void redirectToExplain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentUrl = request.getRequestURL().toString();
        if (!Strings.isNullOrEmpty(request.getQueryString())) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        Matcher matcher = rewritePattern.matcher(currentUrl);
        currentUrl = matcher.replaceAll("/");

        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("http://" + commonConstants.getMainSite() + "/errpage/explain?target={target}").build();
        URI uri = uriComponents.expand(currentUrl).encode().toUri();
        response.sendRedirect(uri.toString());
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return Objects.equal(request.getHeader(HttpHeaders.X_REQUESTED_WITH), "XMLHttpRequest");
    }

    public static class AuthItem {
        public final Pattern pattern;

        public final Set<BaseUser.TYPE> roles;

        public AuthItem(Pattern pattern, Set<BaseUser.TYPE> roles) {
            this.pattern = pattern;
            this.roles = roles;
        }
    }

    public static class WhiteItem {
        public final Pattern pattern;

        public final Set<String> httpMethods;

        public WhiteItem(Pattern pattern, Set<String> httpMethods) {
            this.pattern = pattern;
            this.httpMethods = httpMethods;
        }
    }
}
