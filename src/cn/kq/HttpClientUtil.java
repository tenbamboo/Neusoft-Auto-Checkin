package cn.kq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	
	
	
	public  static Map<String, Object> postForCookie(String url, List<NameValuePair> formparams) {
		return HttpClientUtil.postProxy(url,formparams,"");
	}
	public  static String post(String url, List<NameValuePair> formparams,String jsessionid) {
		return HttpClientUtil.postProxy(url,formparams,jsessionid).get("result").toString();
	}
	

	private  static Map<String,Object> postProxy(String url, List<NameValuePair> formparams,String jsessionid) {
		CloseableHttpClient httpclient =HttpClients.createDefault();
		 Map<String,Object> res = new HashMap<String,Object>();
		HttpPost httppost = new HttpPost(url);
		if("".equals(jsessionid) || null == jsessionid){
			
		}else{
			httppost.addHeader("Cookie", jsessionid);
		}
		
		if (formparams == null) {
			formparams = new ArrayList<NameValuePair>();
		}
		UrlEncodedFormEntity uefEntity;
		try {

			uefEntity = new UrlEncodedFormEntity(formparams);
			httppost.setEntity(uefEntity);
			
//			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					res.put("result", EntityUtils.toString(entity, "UTF-8"));
					
				}
				if("".equals(jsessionid) || null == jsessionid){
					String setCookie = response.getFirstHeader("Set-Cookie").getValue();
					String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
					res.put("cookie", "JSESSIONID="+JSESSIONID);
				}
				
				
				
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return res;
	}


}
