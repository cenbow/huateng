package cn.com.huateng.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64DealImageUtil {
	
	 private final static Logger _log = LoggerFactory.getLogger(Base64DealImageUtil.class);
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
		// 读取图片信息
		BASE64Encoder encoder = new BASE64Encoder();
		byte[] imageBytes;
		InputStream in;
		try {
			in = new FileInputStream(fileImage);
			imageBytes = new byte[in.available()];
			in.read(imageBytes);
			in.close();
			String encodedImageInfo = encoder.encode(imageBytes);
			return encodedImageInfo;
		} catch (FileNotFoundException e) {
			_log.error("=====指定的照片信息不存在=====" + e.getMessage());
			return null;
		} catch (IOException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return null;
		}
	}

    public static String readImage(InputStream stream)throws Exception{
        // 读取图片信息
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            byte[] imageBytes = new byte[stream.available()];
            stream.read(imageBytes);
            stream.close();
            String encodedImageInfo = encoder.encode(imageBytes);
            return encodedImageInfo;
        } catch (Exception e) {
            if(stream!=null){
                stream.close();
            }
            _log.error("=====解析照片信息出错=====" + e.getMessage());
            return null;
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
			file.mkdir();
		}
		// 开始解析照片
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] imageBytes;
		try {
			imageBytes = decoder.decodeBuffer(encodedImageInfo);
		} catch (IOException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return false;
		}
		// 开始存储照片
		OutputStream out;
		try {
			File imageFile = new File(filePath + "/" + imageName);
			out = new FileOutputStream(imageFile);
			out.write(imageBytes);
			out.flush();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return false;
		} catch (IOException e) {
			_log.error("=====解析照片信息出错=====" + e.getMessage());
			return false;
		}
	}
}
