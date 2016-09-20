package cn.com.huateng.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Desc: 通用Http工具类
 * Author: lixiaoxiao
 * Date: 8/6/13 15:37 AM
 */
public class HttpUtil {
	private final static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	  /**
	   * 获取IP地址
	   * @param request 请求
	   * @return Ip地址
	   * @author huateng
	   */
	  public static String getIpAddr(HttpServletRequest request) {
	        String ipAddress = null;
	        ipAddress = request.getHeader("x-forwarded-for");
	        
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	        	
	        	ipAddress = request.getHeader("Proxy-Client-IP");
	        	log.info("Proxy-Client-IP" + ipAddress);
	        }
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	            ipAddress = request.getHeader("WL-Proxy-Client-IP");
	            log.info("WL-Proxy-Client-IP" + ipAddress);
	        }
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	        	ipAddress = request.getRemoteAddr();
	        	if(ipAddress.equals("127.0.0.1")){
	        		//根据网卡取本机配置的IP
	        		InetAddress inet=null;
	        		try {
	        			inet = InetAddress.getLocalHost();
	        		}catch (UnknownHostException e) {
	        			e.printStackTrace();
	        		}
	        		ipAddress= inet.getHostAddress();
	        	}
	        }
	        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	        if(ipAddress!=null && ipAddress.length()>15){ 
	        	//"***.***.***.***".length() = 15
	            if(ipAddress.indexOf(",")>0){
	                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
	            }
	        }
	        return ipAddress; 
	     }
}
