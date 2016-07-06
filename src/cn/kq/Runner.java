package cn.kq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("userName", "aaa");
		map.put("password", "apassword");
		list.add(map);
		map= new HashMap<String,Object>();
		map.put("userName", "bbb");
		map.put("password", "bpassword");
		list.add(map);
		map= new HashMap<String,Object>();
		map.put("userName", "ccc");
		map.put("password", "cpassword");
		list.add(map);
		Doge dog=new Doge();
		dog.start(list);
	}
	

}	
