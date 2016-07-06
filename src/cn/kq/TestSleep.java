package cn.kq;

import java.util.Random;

public class TestSleep {
	
	public static void main(String[] args) throws InterruptedException {
		
		
		Random ra=new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(ra.nextInt(7)+1);;
		}
		
	}

}
