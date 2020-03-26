package com.ding.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author : xujinding
 * @description : HttpUtil
 * @date : 2017/03/22 15:04
 */
public class HttpUtil {
	
	private static final Log	log			= LogFactory.getLog(HttpUtil.class);
	public static final int		TIME_OUT	= 60000;
	
	/**
	 * http get request
	 */
	public static String get(String urlAddr, Map<String, Object> paramsMap, Integer connectTimeout,
			Integer readTimeout) throws Exception {
		log.info("get request # url：" + urlAddr);
		String line;
		String params = "";
		HttpURLConnection conn = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		StringBuffer result = new StringBuffer();
		try {
			if (connectTimeout == null ) {
				connectTimeout = 60000;
			}
			if (readTimeout == 1) {
				readTimeout = 60000;
			}
			if (paramsMap != null && paramsMap.size() > 0) {
				StringBuffer str = new StringBuffer();
				Set set = paramsMap.keySet();
				Iterator iter = set.iterator();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					String value = paramsMap.get(key) == null ? "" : paramsMap.get(key).toString();
					str.append(key).append("=").append(value).append("&");
				}
				params = "?" + str.toString().substring(0, str.length() - 1);
			}
			URL url = new URL(urlAddr + params);
			conn = (HttpURLConnection) url.openConnection();
			// 设置读取超时时间
			conn.setReadTimeout(readTimeout);
			// 设置连接超时时间
			conn.setConnectTimeout(connectTimeout);
			conn.connect();
			inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * http post request
	 */
	public static String post(String urlAddr, Map<String, Object> paramsMap, Integer connectTimeout,
			Integer readTimeout) throws Exception {
		log.info("post request # url：" + urlAddr);
		String line;
		String params = "";
		HttpURLConnection conn = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		OutputStreamWriter outputStreamWriter = null;
		StringBuffer result = new StringBuffer();
		try {
			if (connectTimeout == null) {
				connectTimeout = 60000;
			}
			if (readTimeout == null) {
				readTimeout = 60000;
			}
			if (paramsMap != null && paramsMap.size() > 0) {
				StringBuffer str = new StringBuffer();
				Set set = paramsMap.keySet();
				Iterator iter = set.iterator();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					String value = paramsMap.get(key) == null ? "" : paramsMap.get(key).toString();
					str.append(key).append("=").append(value).append("&");
				}
				params = str.toString().substring(0, str.length() - 1);
			}
			URL url = new URL(urlAddr);
			conn = (HttpURLConnection) url.openConnection();
			// 设置读取超时时间
			conn.setReadTimeout(readTimeout);
			// 设置连接超时时间
			conn.setConnectTimeout(connectTimeout);
			// 设置是否向HttpURLConnection输出，因为这个是post请求，参数要放在http正文内，
			// 因此需要设为true, 默认情况下是false
			conn.setDoOutput(true);
			// 不使用缓存,默认情况下是true
			conn.setUseCaches(false);
			// 设定请求的方法为"POST",默认是GET
			conn.setRequestMethod("POST");
			if (params.length() > 0) {
				// 此处getOutputStream会隐含的进行connect()
				outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
				// 写入
				outputStreamWriter.write(params);
				// 刷新该流的缓冲
				outputStreamWriter.flush();
			}
			inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * 返回String 类型数据
	 * 
	 * @Title: doRequest
	 * @author xujinding
	 * @date 2017年04月05日 下午6:07:33
	 * @param requestUrl 调用url
	 * @param requestMethod 调用方法
	 * @param params 参数
	 * @param timeOut 超时时间
	 * @return
	 * @return String
	 */
	public static String doRequest(String requestUrl, String requestMethod, String params,
			Integer timeOut) {
		
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if (timeOut == null) {
				timeOut = 60000;
			}
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			// conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != params) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(params.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("连接超时：", ce);
		} catch (Exception e) {
			log.error("https请求异常：", e);
		}
		return null;
	}
}
