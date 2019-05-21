package com.example.demo.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Tengxun2 {
    public static void main(String[] args) {

        File file = new File("D:\\TX\\tx.txt");
        List<String> list = txt2String(file);
        int j = 0;
        String url = "https://v.qq.com/x/search/?q=";
        for (String item : list) {
            j++;
            try {
                Thread.sleep(720);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url2 = url+item;
            //https://v.qq.com/x/search/?ses=qid%3DqhP2ftvba1imXRkeg434K5NuXADPS8dQJE3IB2Q5KXQfuc2tnbs5mQ%26last_query%3D%E8%8A%B1%E7%81%AB%26tabid_list%3D0%7C2%7C1%7C3%7C4%7C11%7C6%7C12%7C21%7C14%7C5%7C17%7C8%7C15%7C20%7C106%7C7%7C1101%26tabname_list%3D%E5%85%A8%E9%83%A8%7C%E7%94%B5%E8%A7%86%E5%89%A7%7C%E7%94%B5%E5%BD%B1%7C%E7%BB%BC%E8%89%BA%7C%E5%8A%A8%E6%BC%AB%7C%E6%96%B0%E9%97%BB%7C%E7%BA%AA%E5%BD%95%E7%89%87%7C%E5%A8%B1%E4%B9%90%7C%E6%B1%BD%E8%BD%A6%7C%E4%BD%93%E8%82%B2%7C%E9%9F%B3%E4%B9%90%7C%E6%B8%B8%E6%88%8F%7C%E5%8E%9F%E5%88%9B%7C%E6%95%99%E8%82%B2%7C%E6%AF%8D%E5%A9%B4%7C%E5%B0%91%E5%84%BF%7C%E5%85%B6%E4%BB%96%7Cyoo%E8%A7%86%E9%A2%91%26resolution_tabid_list%3D0%7C1%7C2%7C3%7C4%7C5%26resolution_tabname_list%3D%E5%85%A8%E9%83%A8%7C%E6%A0%87%E6%B8%85%7C%E9%AB%98%E6%B8%85%7C%E8%B6%85%E6%B8%85%7C%E8%93%9D%E5%85%89%7CVR&q=%E8%8A%B1%E7%81%AB&stag=4&filter=sort%3D0%26pubfilter%3D0%26duration%3D0%26tabid%3D2%26resolution%3D0#!filtering=1
            System.out.println("链接1：" + url2);
            String html = getHtml(url2);
            if(html.isEmpty()){
                System.out.println("===链接无效===");
                contentToTxt("d://TX/tx2.txt", item+"->");
                continue;
            }
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select(".result_item");//循环视频块
            String typename = item+"->";
            for(Element element:elements){
                String tengxun = element.select(".icon_text span").text();
                //System.out.println("腾讯视频："+tengxun);
                String nums = element.select(".result_episode_list").text();
                String title = element.select(".result_title a").text();
                //符合条件的进入链接
                if((!tengxun.equals("腾讯视频"))&&(!nums.isEmpty())){
                    typename += ("||"+title+":");
                    String link = element.select(".result_title a").attr("href");
                    System.out.println("进入链接："+link);
                    String html2 = getHtml(link);
                    Document doc2 = Jsoup.parse(html2);
                    //System.out.println(doc2);
                    //再循环标签
                    Elements elements2 = doc2.select(".tag_list .tag");
                    int typenum = elements2.size();

                    for(int i = 0;i<typenum;i++){
                        typename += tengxun+"：";
                        typename += elements2.get(i).text();
                        typename +="/";
                    }
                }
                System.out.println("。。。");
            }
            contentToTxt("d://TX/tx2.txt", typename);
        }

    }
    /*
     * 写入文档
     * */
    public static void contentToTxt(String filePath, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                    filePath), true));
            writer.write("\n" + content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 按行读取数据
     * */
    public static List<String> txt2String(File file) {
        List<String> list = new ArrayList<String>();
        // StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                // result.append(System.lineSeparator()+s);
                list.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /*
     *
     * 获取静态页面
     * */
    public static String getHtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");//UA[(int) (Math.random()*UA.length)]
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        //request.setHeader("content-type", "text/plain;charset=gb2312");
        request.setHeader("content-type", "text/plain;charset=utf-8");
        //request.setHeader("content-type", "text/plain;charset=big5");
        try {
            HttpResponse response = client.execute(request);
            //htmlContent = EntityUtils.toString(response.getEntity());
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            //返回数据的乱码问题
            //htmlContent = new String(htmlContent.getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }
}
