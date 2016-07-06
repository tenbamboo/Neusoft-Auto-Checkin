package cn.kq;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoup {
	
	
	public static void main(String[] args) throws IOException {
		File input = new File("tmp/1.html");
		Document doc = Jsoup.parse(input, "UTF-8");
		
		System.out.println(doc.select(".kq-message-table tbody tr").size());;
		
		
		
		Elements es=doc.select("input[name='currentempoid']");
		for (Element e : es) {
			System.out.println(e.val());
		}
		
	}

}
