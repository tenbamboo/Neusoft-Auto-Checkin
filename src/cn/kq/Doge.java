package cn.kq;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Doge {
	
	/**
	 * @todo 主入口
	 * @param list
	 * @throws InterruptedException
	 */
	public void start(List<Map<String,Object>> list) throws InterruptedException{
		Doge run=new Doge();
		Random ra=new Random();
		for (Map<String, Object> map : list) {
			int raNum=ra.nextInt(7)+1;
			System.out.println("成员【"+map.get("userName").toString()+"】沉睡【"+raNum+"秒】。。。。。");
			Thread.sleep(raNum*1000);
			try {
				run.go(map.get("userName").toString(), map.get("password").toString(), run);
			} catch (Exception e) {
				System.out.println("成员【"+map.get("userName").toString()+"】发生异常【5秒】重新发涩。。。。。");
				Thread.sleep(5000);
				try {
					run.go(map.get("userName").toString(), map.get("password").toString(), run);
				} catch (Exception e1) {
					System.out.println("成员【"+map.get("userName").toString()+"】再次发生异常【5秒】重新再次发涩,如果再次异常则无视。。。。。");
					Thread.sleep(5000);
					try {
						run.go(map.get("userName").toString(), map.get("password").toString(), run);
					} catch (Exception e2) {
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						System.out.println("成员【"+map.get("userName").toString()+"】签到失败，请手动签到");
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
				}
			}
		}
	}
	
	
	/**
	 * @todo 开始主流程
	 * @param userName
	 * @param password
	 * @param run
	 * @throws Exception 
	 */
	private void go(String userName,String password,Doge run) throws Exception{
		System.out.println("成员【"+userName+"】已加入超值午餐。。。。。");
		
		Map<String,Object> map=HttpClientUtil.postForCookie("http://kq.neusoft.com/", null);
		
		String res=	map.get("result").toString();
		String cookie=map.get("cookie").toString();
		
	
	   Boolean loginFlag=run.login(res, cookie, userName, password,run);
		
		if(loginFlag){
			Map<String,Object> m1=run.gotoCheckPageAndCheckData(cookie);
			if("true".equals(m1.get("result").toString())){
				run.checkInOrOutAndExit(m1.get("oid").toString(),cookie,run);
			}else{
				throw new Exception();
			}
		}else{
			throw new Exception();
		}
		System.out.println("成员【"+userName+"】已退出了超值午餐。。。。。");
	}
	
	
	/**
	 * @todo 登陆
	 * @param res
	 * @param cookie
	 * @param userName
	 * @param password
	 * @param run
	 * @return
	 */
	private Boolean login(String res,String cookie,String userName,String password,Doge run){
		
		Document doc = Jsoup.parse(res);
		System.out.println("获取汪星信息中。。。。。");
		String userNameKey = doc.select(".textfield[type='text']").attr("name"); //取得input的Name
		
		String passwordKey = doc.select(".textfield[type='password']").attr("name");//取得input的Name
		
		System.out.println("成功获取汪星信息！！！！！");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair(userNameKey, userName));  
		formparams.add(new BasicNameValuePair(passwordKey, password));  
		formparams.add(new BasicNameValuePair("login", "true"));  
		
		System.out.println("正在登陆汪星核心系统.....");
		
		
		String r1= HttpClientUtil.post("http://kq.neusoft.com/login.jsp",formparams,cookie);
				 
		if(run.validateOper(r1)){
			System.out.println("已成功潜入汪星核心系统！！！！！");
			return true;
		}else{
			
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("已被汪星系统拦截下来！！！！！");
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			
			return false;
		}
		 
	}
	
	
	/**
	 * @todo 前往签到页面并检查数据
	 * @param cookie
	 * @return
	 */
	private Map<String,Object> gotoCheckPageAndCheckData(String cookie){
		 System.out.println("正在校验今天是否打卡......");
		 String res= HttpClientUtil.post("http://kq.neusoft.com/attendance.jsp", null,cookie);
		 Document  doc = Jsoup.parse(res);
		 Map<String,Object> result=new HashMap<String,Object>();
		 result.put("result", false);
		 
		 Calendar c=Calendar.getInstance();
		 int hour=c.get(Calendar.HOUR_OF_DAY);
		 if(hour < 18 && hour > 12){
			 
			 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			 System.out.println("还没到到下班点，请勿打卡！！！！！");
			 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			 
		 }else if(hour < 24 && hour > 17 &&  doc.select(".kq-message-table tbody tr").size() == 1){
			 System.out.println("√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√");
			 System.out.println("校验打卡成功 ,赶紧下班吧！！！！！！！！！！！！！！！");
			 System.out.println("√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√");
			 result.put("oid",  doc.select("input[name='currentempoid']").val());
			 result.put("result", true);
			 
		 }else if("今天还没有打卡记录".equals(doc.select(".kq-message-table tbody tr td").html()) ){
			 System.out.println("√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√");
			 System.out.println("校验打卡成功 ,加油工作!!!!!!!!!!!!!!!");
			 System.out.println("√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√");
			 result.put("oid",  doc.select("input[name='currentempoid']").val());
			 result.put("result", true);
		 }else if(!"今天还没有打卡记录".equals(doc.select(".kq-message-table tbody tr td").html()) && doc.select(".kq-message-table tbody tr").size() >= 1){
			 
			 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			 System.out.println("今天已经成功打过卡1次卡了");
			 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			 
		 }
		 return result;
	}
	
	
	
	/**
	 * @todo 签到并退出
	 * @param oid
	 * @param cookie
	 * @param run
	 */
	private void checkInOrOutAndExit(String oid,String cookie,Doge run){
		System.out.println("打开汪星系统签到中.....");
		//签到
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("currentempoid", oid));  
		String res = HttpClientUtil.post("http://kq.neusoft.com/record.jsp", formparams,cookie);
		if(run.validateOper(res)){
			 System.out.println("汪星系统签到成功!!!!!");
			 System.out.println("正在撤离汪星.....");
			 res=	HttpClientUtil.post("http://kq.neusoft.com/close.jsp", null,cookie);
			 System.out.println("撤离汪星成功!!!!!");
		}else{
			
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("汪星系统签到失败，3分钟时限之后再试!!!!!");
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
		
	}
	
	/**
	 * @todo 校验操作是否成功
	 * @param res
	 * @return
	 */
	private boolean validateOper(String res){
		Document doc = Jsoup.parse(res);
		int isLogin=doc.select("a[href='http://kq.neusoft.com/attendance.jsp']").size();
		return isLogin == 1 ?true :false;
	}
	
}
