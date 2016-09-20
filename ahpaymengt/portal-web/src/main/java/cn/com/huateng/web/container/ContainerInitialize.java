package cn.com.huateng.web.container;


import cn.com.huateng.common.BaseEnum;
import cn.com.huateng.util.StringUtil;
import com.aixforce.common.utils.NumberUtils;
import com.aixforce.site.handlebars.HandlebarEngine;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 7/29/13 6:18 PM
 */
@Service
public class    ContainerInitialize {

    @Autowired
    private HandlebarEngine handlebarEngine;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    @PostConstruct
    public void init() {
        //格式化金额  eg:{{formatMoney money}}
        handlebarEngine.registerHelper("formatMoney", new Helper<String>() {

            @Override
            public CharSequence apply(String money, Options options) throws IOException {
            	Double price = Double.parseDouble(money);
                return NumberUtils.formatPrice(price);
            }
        });

        
        //格式化金额  eg:{{formatAmount money}},为空时返回为0
        handlebarEngine.registerHelper("formatAmount", new Helper<Number>() {

            @Override
            public CharSequence apply(Number money, Options options) throws IOException {
            	if (money==null){
            		return "0.00";
            	}
            	Double price = Double.parseDouble(money.toString());
                return df.format(price.doubleValue() / 100);
            }
        });
        //分支判断：大于等于
        //e.g:
        //{{#gte 20}}
        // ....something here ...
        //{{/gte}}
        handlebarEngine.registerHelper("gte", new Helper<Number>() {
            @Override
            public CharSequence apply(Number source, Options options) throws IOException {
            	boolean isGte = false;

	             if(source instanceof Double){
                     if(options.param(0).getClass()==String.class){
                         isGte = source.doubleValue() >= Double.parseDouble((String)options.param(0));
                     }else {
                         isGte = source.doubleValue() >=  ((Double) options.param(0));
                     }
	             }
            	 if(source instanceof Float){
            		 isGte = source.floatValue() >= (Float) options.param(0);
            	 }
            	 if(source instanceof Long){
            		 isGte = source.longValue() >= (Long) options.param(0);
            	 }
            	 if(source instanceof Integer){
            		 isGte = source.longValue() >= (Integer) options.param(0);
            	 }
            	 if(source instanceof Short){
            		 isGte = source.longValue() >= (Short) options.param(0);
            	 }

                 if (isGte)
                    return options.fn();
                 else
                    return options.inverse();
            }
        });

        //格式化日期  e.g:{{formatDate date 'day'}}
        handlebarEngine.registerHelper("formatDate", new Helper<Date>() {
            Map<String, SimpleDateFormat> sdfMap = ImmutableMap.of(
                    "gmt", new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy"),
                    "day", new SimpleDateFormat("yyyy-MM-dd"),
                    "default", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                    "minute", new SimpleDateFormat("yyyy-MM-dd HH:mm"));

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


        //获取参数中包含的文件名称,eg {{format filepath}}
        handlebarEngine.registerHelper("getFilename", new Helper<String>() {
            @Override
            public CharSequence apply(String filePath, Options options) throws IOException {
                return StringUtil.getFilename(filePath);
            }
        });

        handlebarEngine.registerHelper("dismatch", new Helper<String>() {
            @Override
            public CharSequence apply(String regEx, Options options) throws IOException {
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher((String) options.param(0));
                if (!mat.find())
                    return options.fn();
                else
                    return options.inverse();
            }
        });
        
        handlebarEngine.registerHelper("explain", new Helper<String>() {
            @SuppressWarnings("rawtypes")
			@Override
            public CharSequence apply(String value,Options options) throws IOException {
            	String enumName =  options.param(0);
            	String classPath = "cn.com.huateng.common." + enumName;
            	try {
            		 Class clazz = Class.forName(classPath);
            		 Object[] enumConstants = clazz.getEnumConstants();
            		 for (Object t : enumConstants) {  
        	            if (((BaseEnum) t).getValue().equals(value)) {  
        	                return ((BaseEnum) t).getDisplay();  
        	            }  
            	     }  
					 return value;
				} catch (Exception e) {
					e.printStackTrace();
				} 
            	
            	return null;
            }
        });

        handlebarEngine.registerHelper("transformat", new Helper<String>() {

            @Override
            public CharSequence apply(String value, Options options) throws IOException {
                if(value.length()>10){
                    return value.substring(0,10)+"...";
                }else{
                    return value;
                }
            }
        });

        handlebarEngine.registerHelper("announceformat", new Helper<String>() {

            @Override
            public CharSequence apply(String value, Options options) throws IOException {
                if(value.length()>30){
                    return value.substring(0,30)+"......";
                }else{
                    return value;
                }
            }
        });
        
    }
}
