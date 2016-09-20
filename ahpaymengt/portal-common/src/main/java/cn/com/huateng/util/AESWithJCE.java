package cn.com.huateng.util;

import cn.com.huateng.util.ocx.Rijndael;
import org.apache.commons.codec.binary.Base64;



public class AESWithJCE {

	public AESWithJCE() {
		
	}
	public    static  String  getResult(String transferKey,String data){
		
		final String saltKey = "a7fc844d17f43955783d7d6f5df7eb4e";	
		try
		{
        	// Base64解码得到密文
			Base64 base64 = new Base64();
			if(data.contains(" ")){
				data =data.replaceAll(" ","+");
			}
			byte[] cypherArray = base64.decode(data.getBytes());
			
			// 用saltKey去解密transferKey得到中间序列
			Rijndael aes256 = new Rijndael();
			
			aes256.makeKey(saltKey.getBytes(), saltKey.length() * 8);
			
			// 由于采用的是NoPadding的方式，确保transferKey一定是128bits的整数倍。
			byte[] tmp = aes256.decryptArrayNP(transferKey.getBytes(), 0);
			
			// 编码后的中间序列进行Base64编码作为真实的解密密钥
			byte[] realKey = new byte[32];
			System.arraycopy(base64.encode(tmp), 0, realKey, 0, 32);
			aes256.makeKey(realKey, realKey.length * 8);
			
			// 解密密文
			byte[] plainArray = aes256.decryptArrayNP(cypherArray, 0);
			String plainText = new String(plainArray).trim();
			
			return plainText;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
	//	String key="j0nuckvu2zhvcaxc28ckykm2xyx0xkvk";//key 
     //  String data="58bN9Iln5HjmwBn9dQxa6Q==";
		//String key="14067685175191402574483948278987";
		//String   data="jchjnv77nhV4D8dGfouwNA8BRsjOg5i/ieikWWvkZPFmazB14yVfRnOCEALSfBytWgk/s424LeMugfdsNhvTdY0SAFA2T0dxQzpJ5AsaVTPCR16G6uftqIzGhQZnXiLFk1iRA ka9hJ9/lM fccM0bfhqLMXRRWBmp2IeUBQO4f/  jtfDsi/XyA0 4rwgrls dIfxcu100OVWaw962OTNXz2nw9kc68K5ygRxdyTQyliKxFCCEo1Ad2v/iP0mjH0tgNYTUsrZbX7Vmkxs8mvNMnEL3cLnWWEgKkUOK1h8QmhiqQQraMgTv7fyLECAcu4d3xvN3zytAPqdoIpBbOWrgGUbhut9qXAMiRgoyM/niGT/MzuYwndg93blWZxLG7";
		//String data="JeCUj7TVgkWhu1SGKWEkK7ucwCVEvdQo/cIrPh4CIY++3otnu+oXMb5agakXHlLUhu9c96pRTxq6/HRL+8Y8HadwvYFvqVIOm0KC 0345orgfohiWnDFhRdwR8Whf8RhmXx8BVNWcWlSPs0CDjnhRJvGxBfq5YN556wTDAQtL02m2zoUTxNt5dLD/TK0 AysR9rZix t BzEQmI1BFVnS6KT5HtpRJdioZ3Q55fnrnk6Bo6gfO4EXSy1nKGXThCApeQprYTg9adHCRSrvnss9Ur5/uZYIrq9RyRzBuAewdKwpTXb0eMx5cj0xzLfjolQlhzAjPUOXO/a jvkB7FyIQsQrtnFOfh3RvaSzXp9aX4/h/ZrQ6//wrJn7kNdB9e8";//data
		//String data="q ZP6iXtliQ9iFNbF0VXyw==";//data
    	String key="0hxy28hvcvemcxgnjuyngkjgyxgexyju";//key 
        String data="YwIUkH4qjTuKPkGPyVbL/SE1acl4t1FolR86tmsXlNwTj70snKW1e30xsLC+MUFeXdTp2Rw8lCZiQYgj9KMmCv4Z5PKiPD2pVY3Ltew2GtnIpREALMFPNvrfzSMsy+CnhzN9Qbb9DfJahRwQuDQIfJku6cRnlw3RudoYKQRp+2NB46/s1ug8qZVLpp63JdkxVNQedbrMFt6CmSoECSVGB5ZhChLXrEc1XL14452s9edtBt0uy/ggP+BE6tsOwIv6s54dAAj3cz/6zxRNybxs7VdYUV7C5ecot1nd3A735JF32h0ZKUzguaOVE2+YCaASPYdjjsGxQGyshBUNnDAkdiAIQKmFQosZFBtwFc+5QH0RCv+n17d6MQU1oN+Eb4wq==";//data
		if(data.contains(" ")){
			data =data.replaceAll(" ","+");
		}
//	System.out.println("result :"+data);
     //  System.out.println(key.length());
		 System.out.println("result :"+getResult(key,data));

	}

}
