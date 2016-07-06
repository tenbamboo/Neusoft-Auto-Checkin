package cn.kq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {

	
	
	public static void main(String[] args) {
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
		 
		 HttpPost httppost = new HttpPost("http://kq.neusoft.com"); 
		 
		 List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		  formparams.add(new BasicNameValuePair("KEYyyYvX7SKNhqLV1GpyK74sSzJZdydCG5f8sVlKxztCStpyvXXDwC8!1024886011!1467683530157", "haze123!"));  
		  formparams.add(new BasicNameValuePair("IDyyYvX7SKNhqLV1GpyK74sSzJZdydCG5f8sVlKxztCStpyvXXDwC8!1024886011!1467683530157", "liu.xinyuan"));  
		  formparams.add(new BasicNameValuePair("login", "true"));  
		 
	     UrlEncodedFormEntity uefEntity;  
	     try {  
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	            httppost.setEntity(uefEntity);  
	            System.out.println("executing request " + httppost.getURI());  
	            CloseableHttpResponse response = httpclient.execute(httppost);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                if (entity != null) {  
	                    System.out.println("--------------------------------------");  
	                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
	                    System.out.println("--------------------------------------");  
	                }  
	                String setCookie = response.getFirstHeader("Set-Cookie") .getValue();
	                String JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
	                System.out.println("JSESSIONID:" + JSESSIONID);
	                
	                
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
	    }  
		 
}
