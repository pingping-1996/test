package com.example.demo.test;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class crawlYjj {

	// 国产药品 215900 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.setProperty("webdriver.chrome.driver","D:\\安装软件\\F盘- study\\selenium驱动\\chromedriver_win32\\chromedriver.exe"); 
			WebDriver driver = new ChromeDriver();                 
			String url ="https://baike.baidu.com/item/%E8%8A%B1%E7%81%AB/16551545?fr=aladdin";
			//open Chrome web browser         
			//中国药监局
			int size=215900;
			String baseinfo="";
			for(int s=479; s<=size; s++){
				driver.get(url+s);
		        // 首先得到表格中所有tr标签的集合
		        WebElement rows = driver.findElements(By.xpath("//table")).get(0);//国产药品网页中有2个table，获取第一个table，第二个table是图片
		        List<WebElement> e_row = rows.findElements(By.tagName("tr"));//获取每一行数据
		        System.out.println("总行数为》》"+e_row.size());
		        for(int i=1;i<e_row.size();i++){
		        	WebElement theRow = e_row.get(i);  
	//	          //调用getCell方法得到对应的列对象，然后得到要查询的文本。  
		        	List<WebElement> e_col = theRow.findElements(By.tagName("td"));
	//	            String key = e_col.get(0).getText(); 
		        	if(e_col.size()>1){//有2个td时，是有数据，一个td时，无信息
    		            String value = e_col.get(1).getText(); 
    		            baseinfo=value.replace(",", "，").replace("\n", "")+",";
		        	}
		        }
		        Thread.sleep(5000);
			}
	        System.out.println(baseinfo);
	        String baseinfoname = "d:\\药监局.csv";
	        getFiles(baseinfoname, baseinfo);
//	       driver.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}         
	}
	public static void getFiles(String pathfilname, String type) {
		FileOutputStream fos;
		OutputStreamWriter osw;
		try {
			fos = new FileOutputStream(pathfilname,true);
			osw = new OutputStreamWriter(fos, "gbk");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.append(type);
			bw.newLine();
			bw.flush();
			osw.flush();
			fos.flush();
			fos.close();
			osw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
 

