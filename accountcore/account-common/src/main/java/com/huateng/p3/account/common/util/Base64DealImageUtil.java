package com.huateng.p3.account.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

public class Base64DealImageUtil {
	private static Logger _log = Logger.getLogger(Base64DealImageUtil.class);

	public static String readImageFromFile(String filePath, String imageName) {
		
		// 判断存储路径是否存在，不存在则直接返回null
		File file = new File(filePath);
		if (!file.exists()) {
			_log.error("=====存储照片信息的目录不存在=====");
			return null;
		}
		// 判断照片是否存在，不存在则直接返回null
		File fileImage = new File(filePath + "/" + imageName);
		if (!fileImage.exists()) {
			_log.error("=====指定的照片信息不存在=====");
			return null;
		}
		
		byte[] imageBytes;
		InputStream in = null;
		try {
			in = new FileInputStream(fileImage);
			imageBytes = new byte[in.available()];
			in.read(imageBytes);
			// 读取图片信息
			String encodedImageInfo = Base64.encodeBase64String(imageBytes);
			return encodedImageInfo;
		} catch (FileNotFoundException e) {
			_log.error("=====指定的照片信息不存在=====" + e.getMessage());
			return null;
		} catch (IOException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return null;
		}finally{
			try {
				if(null!=in){
					in.close();
				}
			} catch (IOException e) {
				_log.error("=====解析照片关闭流出错=====" + e.getMessage());
			}
		}
	}

	public static Boolean writeImageToFile(String filePath, String imageName,
			String encodedImageInfo) {
		// 判断图片是否为空
		if (encodedImageInfo == null || "".equals(encodedImageInfo)) {
			return false;
		}
		// 判断存储路径是否存在，不存在则创建路径
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		byte[] imageBytes;		
		// 开始存储照片
		OutputStream out = null;
		try {
			// 开始解析照片
			imageBytes = Base64.decodeBase64(encodedImageInfo);
			File imageFile = new File(filePath + "/" + imageName);
			out = new FileOutputStream(imageFile);
			out.write(imageBytes);
			out.flush();
		
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return false;
		} catch (IOException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return false;
		}finally{
			try {
				if(null!=out){
					out.close();
				}
			} catch (IOException e) {
				_log.error("=====解析照片关闭流出错=====" + e.getMessage());
			}
		}
	}
}

