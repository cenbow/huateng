/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.handlebars;

import com.aixforce.common.utils.JsonMapper;
import com.aixforce.common.utils.NumberUtils;
import com.aixforce.site.container.ExecutorFactory;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.ComponentService;
import com.aixforce.user.base.UserUtil;
import com.aixforce.user.base.exception.UserNotLoginException;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 9/22/12 9:48 PM
 */
@org.springframework.stereotype.Component
public class HandlebarEngine {
    private static Logger logger = LoggerFactory.getLogger(HandlebarEngine.class);

    private Handlebars handlebars;

    private final ComponentService componentService;

    private ExecutorFactory executorFactory;

    private final TemplateLoader templateLoader;

    //用于本地缓存模块的渲染结果，通过admin配置缓存参数
    private Cache<String, String> executedComponentCache;

    @Getter
    private String nuwaHome;

    @Autowired
    public HandlebarEngine(ComponentService componentService, ExecutorFactory executorFactory,
                           @Value("#{app.nuwaHome}") String nuwaHome, ServletContext servletContext) {
        this.nuwaHome = nuwaHome;
        this.componentService = componentService;
        this.executorFactory = executorFactory;
        this.templateLoader = new ServletAndFileTemplateLoader(servletContext, "/views", ".hbs", nuwaHome);
    }

    @PostConstruct
    public void init() {
        //init cache
        executedComponentCache = CacheBuilder.newBuilder().
                maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).build();

        handlebars = new Handlebars(templateLoader);
        //handlebars.setExposePseudoVariables(true);

        /**
         * 此helper会调用handlebars自身渲染一个组件
         *
         * 参数可自适配组件id或者组件path
         * block内的内容为组件的初始数据，JSON格式
         */
        handlebars.registerHelper("inject", new Helper<String>() {
            @Override
            public CharSequence apply(String compPath, Options options) throws IOException {
                // prepare context
                Map<String, Object> tempContext = Maps.newHashMap();
                if (options.context.model() instanceof Map) {
                    //noinspection unchecked
                    tempContext.putAll((Map<String, Object>) options.context.model());
                }
                if (options.tagType == TagType.SECTION && StringUtils.isNotBlank(options.fn.text())) {
                    //noinspection unchecked
                    Map<String, Object> config = JsonMapper.nonEmptyMapper().fromJson(options.fn.text(), Map.class);
                    if (config != null) {
                        tempContext.putAll(config);
                    }
                }
                Object firstParam = options.param(0, null);
                if (firstParam != null) {
                    if (firstParam instanceof Boolean && (Boolean) firstParam) {
                        // 如果有第二个参数且为true 则认为有后台接口需要调用
                        Component component = componentService.findByPath(compPath);
                        if (component == null) {
                            logger.warn("call api failed, can't find component config for path:" + compPath);
                            return new Handlebars.SafeString(HandlebarEngine.this.exec(null, compPath, tempContext));
                        }
                        return new Handlebars.SafeString(HandlebarEngine.this.execComponent(null, component, tempContext));
                    }
                    if (firstParam instanceof String && StringUtils.isNotBlank((String)firstParam)) {
                        Component component = new Component();
                        component.setPath(compPath);
                        component.setApis(ImmutableMap.of("default", (String)firstParam));
                        return new Handlebars.SafeString(HandlebarEngine.this.execComponent(null, component, tempContext));
                    }
                }
                return new Handlebars.SafeString(HandlebarEngine.this.exec(null, compPath, tempContext));
            }
        });

        handlebars.registerHelper("component", new Helper<String>() {

            @Override
            public CharSequence apply(String className, Options options) throws IOException {
                StringBuilder compOpenTag = new StringBuilder("<div class=\"").append(className).append(" js-comp\"");
                Object id = options.context.get(RenderConstants.ID);
                if (id != null) {
                    compOpenTag.append(" id=\"").append(id).append("\"");
                }
                Object style = options.context.get(RenderConstants.STYLE);
                if (style != null) {
                    compOpenTag.append(" style=\"").append(style).append("\"");
                }
                Object compId = options.context.get(RenderConstants.COMPONENT_ID);
                if (compId != null) {
                    compOpenTag.append(" data-comp-id=\"").append(compId).append("\"");
                }
                Object compPath = options.context.get(RenderConstants.COMPONENT_PATH);
                if (compPath != null) {
                    compOpenTag.append(" data-comp-path=\"").append(compPath).append("\"");
                }
                compOpenTag.append(" >");
                return new Handlebars.SafeString(compOpenTag.toString() + "\n" + options.fn() + "\n</div>");
            }
        });

        handlebars.registerHelper("json", new Helper<Object>() {
            @Override
            public CharSequence apply(Object context, Options options) throws IOException {
                return JsonMapper.nonEmptyMapper().toJson(context);
            }
        });

        handlebars.registerHelper("match", new Helper<String>() {
            @Override
            public CharSequence apply(String regEx, Options options) throws IOException {
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher((String) options.param(0));
                if (mat.find())
                    return options.fn();
                else
                    return options.inverse();
            }
        });
        /**
         * 大于
         */
        handlebars.registerHelper("gt", new Helper<Object>() {
            @Override
            public CharSequence apply(Object source, Options options) throws IOException {

                long _source;
                if (source instanceof Long) {
                    _source = (Long) source;
                } else if (source instanceof Integer) {
                    _source = (Integer) source;
                } else {
                    _source = Long.parseLong((String) source);
                }

                if (_source > (Integer) options.param(0))
                    return options.fn();
                else
                    return options.inverse();
            }
        });
        handlebars.registerHelper("mod", new Helper<Integer>() {
            @Override
            public CharSequence apply(Integer source, Options options) throws IOException {

                if ((source + 1) % (Integer) options.param(0) == 0)
                    return options.fn();
                else
                    return options.inverse();
            }
        });

        handlebars.registerHelper("size", new Helper<Object>() {
            @Override
            public CharSequence apply(Object context, Options options) throws IOException {

                return context == null ? "0" : (context instanceof Collection ? ("" + ((Collection) context).size()) : "0");
            }
        });

        handlebars.registerHelper("equals", new Helper<Object>() {
            @Override
            public CharSequence apply(Object source, Options options) throws IOException {

                if (Objects.equal(String.valueOf(source), String.valueOf(options.param(0))))
                    return options.fn();
                else
                    return options.inverse();

            }
        });

        handlebars.registerHelper("formatDate", new Helper<Date>() {
            Map<String, SimpleDateFormat> sdfMap = ImmutableMap.of(
                    "gmt", new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy"),
                    "day", new SimpleDateFormat("yyyy-MM-dd"),
                    "default", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            @Override
            public CharSequence apply(Date date, Options options) throws IOException {
                if (date == null) {
                    return "";
                }
                String format = options.param(0, "default");
                if (format.equals("ut")) {
                    return Long.toString(date.getTime());
                }
                if (!sdfMap.containsKey(format)) {
                    sdfMap.put(format, new SimpleDateFormat(format));
                }
                return sdfMap.get(format).format(date);
            }
        });

        handlebars.registerHelper("formatPrice", new Helper<Number>() {

            @Override
            public CharSequence apply(Number price, Options options) throws IOException {
                return NumberUtils.formatPrice(price);
            }
        });

        /**
         * 只保留圆角的部分
         */
        handlebars.registerHelper("innerStyle", new Helper<Object>() {
            @Override
            public CharSequence apply(Object context, Options options) throws IOException {

                if (context == null) {
                    return "";
                }

                StringBuilder ret = new StringBuilder();

                String[] styles = ((String) context).split(";");
                for (String style : styles) { //border-radius,border-top-left-radius,...
                    String key = style.split(":")[0];
                    if (key.endsWith("radius")) {
                        ret.append(style).append(";");
                    }
                }
                return ret;
            }
        });

        handlebars.registerHelper("cIndex", new Helper<Integer>() {

            @Override
            public CharSequence apply(Integer context, Options options) throws IOException {
                return "" + (char) (context + 'A');
            }
        });
    }

    public <T> void registerHelper(String name, Helper<T> helper) {
        handlebars.registerHelper(name, helper);
    }

    public String exec(Widget widget, String path, Map<String, Object> params) {
        try {
            return naiveExec(widget, path, params, true);
        } catch (FileNotFoundException e) {
            logger.error("exec handlebars' template failed: " + path, e);
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    public String naiveExec(Widget widget, String path, Map<String, Object> params, boolean isComponent) throws FileNotFoundException {
        Template template;
        try {
            if (params == null) {
                params = Maps.newHashMap();
            }

            if (isComponent) {
                template = handlebars.compile(path + "/view");

                params.put(RenderConstants.COMPONENT_PATH, path);

                if (widget != null) {
                    //style
                    params.put(RenderConstants.STYLE, widget.getStyle());

                    //id
                    params.put(RenderConstants.ID, widget.getId());

                    //component id
                    params.put(RenderConstants.COMPONENT_ID, widget.getCompId());
                }
            } else {
                template = handlebars.compile(path);
                if (!params.containsKey(RenderConstants.PAGE)) {
                    Page page = new Page();
                    page.setPath(path);
                    params.put(RenderConstants.PAGE, page);
                }
            }

            params.put(RenderConstants.USER, UserUtil.getCurrentUser()); //user
            return template.apply(params);

        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, FileNotFoundException.class);
            if (e instanceof HandlebarsException) {
                Throwables.propagateIfInstanceOf(e.getCause(), UserNotLoginException.class);
            }
            logger.error("exec handlebars' template failed: " + path, e);
        }

        return "";
    }

    public String execComponent(final Widget widget, final Component comp, final Map<String, Object> context) {
        if (comp == null) {
            logger.debug("component is null for compId : {}", widget.getCompId());
            return "";
        }
        //TODO 和StorageAdvice那里统一

        String key = null;
        if (widget!=null && Objects.equal(Component.CacheBy.Widget.value(), comp.getCachedBy())) {
            key = "Wid:" + widget.getId();
        } else if (Objects.equal(Component.CacheBy.Component.value(), comp.getCachedBy())) {
            key = "Comp:" + comp.getId();
        }

        if (key != null) {
            try {
                return executedComponentCache.get(key, new Callable<String>() {
                    @Override
                    public String call() {
                        return _execComponent(widget, comp, context);
                    }
                });
            } catch (ExecutionException e) {
                logger.error("ExecComponent {} failed: {}", comp.getPath(), e.getMessage());
                return null;
            }
        } else {
            return _execComponent(widget, comp, context);
        }
    }

    private String _execComponent(final Widget widget, final Component comp, final Map<String, Object> context) {
        if (comp.getApis() != null) {
            Object object = executorFactory.getExecutor().exec(comp.getApis().get(RenderConstants.DEFAULT), context);
            context.put(RenderConstants.DATA, object);
        } else {
            logger.warn("can't find api config for component:" + comp.getPath());
        }
        return exec(widget, comp.getPath(), context);
    }

}