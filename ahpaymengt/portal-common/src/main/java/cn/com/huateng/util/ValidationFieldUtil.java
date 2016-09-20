package cn.com.huateng.util;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * User: 董培基
 * Date: 13-8-1
 * Time: 上午9:27
 */
public class ValidationFieldUtil {

    /*
	intege1:"^[1-9]\\d*$",					//正整数
	num:"^([+-]?)\\d*\\.?\\d+$",			//数字
	decmal:"^([+-]?)\\d*\\.\\d+$",			//浮点数

	email:"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
	color:"^[a-fA-F0-9]{6}$",				//颜色
	url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",	//url
	chinese:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",					//仅中文
	zipcode:"^\\d{6}$",						//邮编
	mobile:"^(13|15)[0-9]{9}$",				//手机
	ip4:"^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]).(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]).(d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]).(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$",				//ip地址
	notempty:"^\\S+$",						//非空
	picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",	//图片
	rar:"(.*)\\.(rar|zip|7zip|tgz)$",								//压缩文件
	date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",					//日期
	qq:"^[1-9]*[1-9][0-9]*$",				//QQ号码
	tel:"(\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})",	//国内电话
	username:"^\\w+$",						//用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter:"^[A-Za-z]+$",					//字母
	letter_u:"^[A-Z]+$",					//大写字母
	letter_l:"^[a-z]+$",					//小写字母
	idcard:"^[1-9]([0-9]{14}|[0-9]{17})$"	//身份证

	*/
    //整数
    public static final String integerRegex="^[0-9]\\d*$";
    public static final Pattern integerPattern = Pattern.compile(integerRegex);
    public static boolean checkInteger(String i){
        return integerPattern.matcher(i).find();
    }
    //浮点数
    public static final String decimalRegex="^[0-9]\\d*(.\\d{1,2})?$|(0.\\d{1,2})";
    public static final Pattern doublePattern = Pattern.compile(decimalRegex);
    public static String checkDouble(String c,@Nullable Object errorMessage){
        if (c == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else if (!doublePattern.matcher(c).find()) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        } else{
            return c;
        }

    }


    //手机号
    public static final String mobileRegex="^(13[0-9]|15[0-9]|18[0-9]|14[7]|17[07])[0-9]{8}$";
    public static final Pattern mobilePattern = Pattern.compile(mobileRegex);
    public static boolean checkMobile(String mobile){
        if(StringUtil.length(mobile)==0){
            return false;
        }
        return mobilePattern.matcher(mobile).find();
    }
    //客户号
    public static final String customerNoRegex="^[A-Za-z0-9]{16}$";
    public static final Pattern customerNoPattern = Pattern.compile(customerNoRegex);
    public static boolean checkCustomerNo(String customerNo){
        return customerNoPattern.matcher(customerNo).find();
    }
    //座机
    public static final String telRegex="^(\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})$";
    public static final Pattern telPattern = Pattern.compile(telRegex);
    public static boolean checkTelephone(String telephone){
        return telPattern.matcher(telephone).find();
    }
    //email
    public static final String emailRegex="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final Pattern emailPattern = Pattern.compile(emailRegex);
    public static boolean checkEmail(String e){
        return emailPattern.matcher(e).find();
    }
    //中文
    public static final String chineseRegex="^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
    public static final Pattern chinesePattern = Pattern.compile(chineseRegex);
    public static boolean checkChinese(String c){
        return chinesePattern.matcher(c).find();
    }
    //邮编
    public static final String zipCodeRegex="^\\d{6}$";
    public static final Pattern zipCodePattern = Pattern.compile(zipCodeRegex);
    public static boolean checkPostCode(String p){
        return zipCodePattern.matcher(p).find();
    }
    //身份证
    public static final String idRegex="^[1-9]([0-9]{14}|[0-9|X|x]{17})$";
    public static final Pattern idPattern = Pattern.compile(idRegex);
    public static boolean checkIdNumber(String idNumber){
        return idPattern.matcher(idNumber).find();
    }
    //过滤特殊字符   ^[\u4E00-\u9FA5\uF900-\uFA2D\|a-z\|A-Z\|0-9]+$
    public static final String cdeRegex="^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\|\\s*|[\\s ]|a-z\\|A-Z\\|0-9\\()（）]*\\s*|[\\s ]+$";
    public static final Pattern cdePattern = Pattern.compile(cdeRegex);
    public static boolean checkCde(String c){
        return cdePattern.matcher(c).find();
    }
    //非空 不能校验null
    public static final String emptyRegex="^\\S+$";
    public static final Pattern emptyPattern = Pattern.compile(emptyRegex);
    public static boolean checkEmpty(String e){
        return emptyPattern.matcher(e).find();
    }
    //密码
    public static final String passwordRegex="^[a-z\\|A-Z\\|0-9]+$";
    public static final Pattern passwordPattern = Pattern.compile(passwordRegex);
    public static boolean checkPassWord(String p){
        return passwordPattern.matcher(p).find();
    }
    //IVR密码
    public static final String ivrPasswordRegex="^[0-9]{6}$";
    public static final Pattern ivrPasswordPattern = Pattern.compile(passwordRegex);
    public static boolean checkIVRPassWord(String IVR){
        return ivrPasswordPattern.matcher(IVR).find();
    }
    //
    /**金钱的匹配模式  小数点后2位*/
    public static final String moneyRegex = "^(?!0$)(?!0.0$)(?!0.00$)(([1-9]\\d*)|0)(\\.\\d{1,2})?$";
    public static final Pattern moneyPattern = Pattern.compile(moneyRegex);
    public static boolean checkMoney(String m){
        return moneyPattern.matcher(m).find();
    }
    //数字和英文字符
    public static final String charRegex="^[a-z\\|A-Z\\|0-9]+$";
    public static final Pattern charPattern = Pattern.compile(charRegex);
    public static String checkChar(String c,@Nullable Object errorMessage){
        if (c == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else if (!charPattern.matcher(c).find()) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        } else{
            return c;
        }

    }
    
    public static boolean checkChar(String c){
		return charPattern.matcher(c).find();
	}

    //中文英文
    public static final String CERegex="[\\u4E00-\\u9FA5\\uF900-\\uFA2D]*[a-z]*[A-Z]*\\d*\\.*\\|*\\,*-*'*:*\\{*\\}*\\[*\\]*\\s*";
    public static final Pattern CEPattern = Pattern.compile(CERegex);
    public static boolean checkChineseEnglish(String c){
        return charPattern.matcher(c).find();

    }
    
    
    //校验是否为电信手机号
    public static final String telecomMobileRegex="^(133|153|189|180|181|177)[0-9]{8}$";
    public static final Pattern telecomMobilePattern = Pattern.compile(telecomMobileRegex);
    public static boolean checkTelecomMobile(String c){
        return telecomMobilePattern.matcher(c).find();

    }

    //校验是否为非电信手机号
    public static final String notTelecomMobileRegex="^(13[0-24-9]|15[0-24-9]|18[2-8]|14[7]|17[0])[0-9]{8}$";
    public static final Pattern notTelecomMobilePattern = Pattern.compile(notTelecomMobileRegex);
    public static boolean checkNotTelecomMobile(String c){
        return notTelecomMobilePattern.matcher(c).find();

    }

}
