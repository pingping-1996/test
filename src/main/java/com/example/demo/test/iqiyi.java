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

public class iqiyi {
    public static void main(String[] args) throws IOException {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        File file = new File("D:\\excel\\腾讯电视剧监听需求 02 01.txt");
        List<String> list = txt2String(file);
        for (int code = 0; code < list.size(); code++){
            String item = list.get(code);
            String url="https://so.iqiyi.com/so/q_"+item+"_ctg_电视剧_t_0_page_1_p_1_qc_0_rd__site_iqiyi_m_1_bitrate_?af=true";
            System.out.println(url);
            String html = getHtml(url);
            Document doc = Jsoup.parse(html);
            Elements listItems = doc.select("ul.mod_result_list li");
            //System.out.println("29行："+listItems);
            String typename = item+"->";
            for(Element listItem : listItems){
                String footer = listItem.select(".info_item_bottom").text();
                String title = listItem.select("h3.result_title").text();
                System.out.println("34行："+footer +"===:==="+ title);
                if(!footer.isEmpty() && title.indexOf(item)!=-1){
                    typename += ("||"+title+":");
                    String target_url = listItem.select("h3.result_title a").attr("href");
                    System.out.println("target_url："+target_url);
                    String detail = getHtml(target_url);
                    Document detail_doc = Jsoup.parse(detail);
                    Elements typelist = detail_doc.select(".episodeIntro-type a");
                    int typenum = typelist.size();

                    for(int i = 0;i<typenum;i++){
                        typename += typelist.get(i).text();
                        typename +="/";
                    }
//                    row.createCell((short) 2).setCellValue(typename);

                }
            }
            System.out.println("code："+code);
            System.out.println("typename："+typename);
            contentToTxt("d://excel/export.txt",
                    typename);// 保存txt
        }
    }
    /*
    * 获取
    * */
    public static String getHtml(String url) throws FileNotFoundException {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();

        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");// UA[(int)
        // (Math.random()*UA.length)]
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept-Charset",
                "utf-8, GBK, GB2312;q=0.7,*;q=0.7");
        request.setHeader("Keep-Alive", "115");
        request.setHeader("Cache-Control", "max-age=0");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Accept-Language", "zh-cn,en-us;q=0.8,zh-tw;q=0.5,en;q=0.3");

        try {
            Thread.sleep(500);
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return htmlContent;
    }
    /*
    * 截取
    * */
    public static String getStrByPrePost(String str, String pre, String post) {
        if (str != null) {
            if (pre != null) {
                int s = str.indexOf(pre);
                if (s > -1) {
                    str = str.substring(s + pre.length(), str.length());
                } else {
                    return null;
                }

            }
            if (post != null) {
                int e = str.indexOf(post);
                if (e > -1) {
                    str = str.substring(0, e);
                } else {
                    return null;
                }

            }
        }
        return str;
    }
    /*
    * 读
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
    * 写
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
}
