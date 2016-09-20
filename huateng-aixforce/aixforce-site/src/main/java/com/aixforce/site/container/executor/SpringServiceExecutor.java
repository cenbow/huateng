/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container.executor;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.exception.ParamInfoException;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.container.ServiceExecutor;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.InnerCookie;
import com.aixforce.user.base.UserUtil;
import com.aixforce.user.base.exception.UserNotLoginException;
import com.google.common.base.Defaults;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Desc: executor based on handlebars
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 7:47 PM
 */
@Component("springServiceExecutor")
public class SpringServiceExecutor implements ServiceExecutor {
    private static Logger logger = LoggerFactory.getLogger(SpringServiceExecutor.class);

    protected static class ServiceInfo {
        private final Class<?> klass;   //类名

        private final Method method;    //方法

        private final Class<?>[] types;  //参数类型数组

        private final String[] names;    //参数名称数组

        public ServiceInfo(Class<?> klass, Method method, Class<?>[] types, String[] names) {
            this.klass = klass;
            this.method = method;
            this.types = types;
            this.names = names;
        }

        public Class<?> getKlass() {
            return klass;
        }

        public Method getMethod() {
            return method;
        }

        public Class<?>[] getTypes() {
            return types;
        }

        public String[] getNames() {
            return names;
        }
    }

    /**
     * api string to service info cache
     */
    private final LoadingCache<String, ServiceInfo> serviceInfos;

    private DefaultConversionService converter = new DefaultConversionService();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private ResultResolver resultResolver;


    public SpringServiceExecutor() {
        CacheLoader<String, ServiceInfo> loader = new CacheLoader<String, ServiceInfo>() {

            @Override
            public ServiceInfo load(String key) throws Exception {

                Iterable<String> parts = Splitter.on(':').trimResults().omitEmptyStrings().split(key);
                if (Iterables.size(parts) != 2) {
                    throw new IllegalArgumentException("bad api format,should be interfaceName:methodName,but is: " + key);
                }
                Class<?> klass = Class.forName(Iterables.get(parts, 0));
                Method method = findMethodByName(klass, Iterables.get(parts, 1));
                if (method == null) {
                    throw new NoSuchMethodException("failed to find method: " + key);
                }

                Class<?>[] types = method.getParameterTypes();

                String[] names = findNamesByAnnotation(method, types.length);

                return new ServiceInfo(klass, method, types, names);
            }

            private String[] findNamesByAnnotation(Method method, int length) {
                String[] names = new String[length];
                Annotation[][] all = method.getParameterAnnotations();
                for (int i = 0; i < length; i++) {
                    Annotation[] annotations = all[i];
                    names[i] = null;
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == ParamInfo.class) {
                            ParamInfo paramInfo = (ParamInfo) annotation;
                            names[i] = paramInfo.value();
                            break;
                        }
                    }
                    if (names[i] == null) {
                        throw new ParamInfoException("all arguments should has ParamInfo annotation,the method is:" +
                                method.getDeclaringClass() + ":" + method.getName());
                    }
                }
                return names;
            }
        };

        this.serviceInfos = CacheBuilder.newBuilder().build(loader);
    }


    @Override
    public Object exec(String api, Map<String, Object> params) {
        if (Strings.isNullOrEmpty(api)) return null;

        try {
            ServiceInfo serviceInfo = serviceInfos.getUnchecked(api);

            Class<?>[] types = serviceInfo.getTypes();
            Object[] concernedParams = new Object[types.length];

            for (int i = 0; i < types.length; i++) {
                Class<?> type = types[i];
                String name = serviceInfo.getNames()[i];
                concernedParams[i] = getParamObject(type, name, params);
            }

            Object bean = applicationContext.getBean(serviceInfo.getKlass());

            Method method = serviceInfo.getMethod();

            Object object = method.invoke(bean, concernedParams);

            if (resultResolver != null)
                return resultResolver.resolver(object);

            return object;
        } catch (IllegalAccessException e) {
            logger.error("illegal access service method,,", e);
        } catch (UserNotLoginException e) {
            throw e; // rethrow the UserNotLoginException to redirect to login page
        } catch (Exception e) {
            logger.error("service call failed,", e);
        }
        return null;
    }

    private Method findMethodByName(Class<?> beanClazz, String methodName) {
        Method[] methods = beanClazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }


    /**
     * 获取一个参数
     *
     * @param paramType 参数类型
     * @param paramName 参数名称
     * @param params    所有的参数
     * @return 参数名称对应的参数值
     */
    private Object getParamObject(Class<?> paramType, String paramName, Map<String, Object> params) {

        if (BaseUser.class.isAssignableFrom(paramType)) {

            if (paramName.equals("seller")) {
                return findSellerBySite(params.get(RenderConstants.SITE));
            }

            Object user = UserUtil.getCurrentUser();
            if (user == null) {
                throw new UserNotLoginException("user not login.");
            }
            return user;
        }


        if (paramType == InnerCookie.class) {
            return UserUtil.getInnerCookie();
        }

        //TODO 创建这两个对象的接口上不能加这两个属性
        if (paramType == Site.class && params.get(RenderConstants.SITE) != null) {
            return params.get(RenderConstants.SITE);
        }

        if (paramType == SiteInstance.class && params.get(RenderConstants.SITE_INSTANCE) != null) {
            return params.get(RenderConstants.SITE_INSTANCE);
        }

        if (Map.class.isAssignableFrom(paramType)) {
            Map<String,String> stringParams = Maps.newHashMap();
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if(value!=null && value instanceof String){
                    stringParams.put(key,value.toString());
                }
            }
            return stringParams;
        }

        Object _param = params.get(paramName);

        if (_param == null) {
            return Defaults.defaultValue(paramType);
        }

        return converter.convert(_param, paramType);
    }

    private Object findSellerBySite(Object site) {
        if (site != null) {
            Site _site = (Site) site;
            return new BaseUser(_site.getUserId(), null, BaseUser.TYPE.SELLER);
        }
        return null;
    }
}