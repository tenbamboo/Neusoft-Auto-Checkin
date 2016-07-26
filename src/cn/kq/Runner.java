package cn.kq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {

	public static void main(String[] args) throws InterruptedException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "username1");
		map.put("password", "password1");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("userName", "username2");
		map.put("password", "password2");
		list.add(map);
		
		
		Doge dog = new Doge();
		dog.start(list);
	}

}
