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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *
 * url的编码解码方法
 * */
public class Baidubaike2 {
    public static void main(String[] args) {
        /*读取excel表格中的所有关键字*/
        File file = new File("D:\\bdbk\\bdbk.txt");
        List<String> list = txt2String(file);
        int j = 0;
        /*拼成url*/
        String url2 = "https://www.baidu.com/s?wd=";
        //String houzhui = "/18841093?fr=aladdin";
        //https://baike.baidu.com/item
        for (String item : list) {
            try {
                Thread.sleep(590);
            } catch (Exception e) {
                e.printStackTrace();
            }
            j++;
            //String url2 = "https://www.baidu.com/s?wd=";
            //String item2 = getURLEncoderString(item);
            String url = url2 + item;
            String name = item;
            System.out.println("拼接url ：" + url);
            String html = gethtml(url);
            if(html.isEmpty()){
                contentToTxt("d://bdbk/bdbkbdbk2.txt", name+"：");
                continue;
            }
            //System.out.println(html);
            Document doc = Jsoup.parse(html);
            /*循环找到所有百度百科匹配的链接*/
            Elements elements = doc.select(".c-container");
            for (Element element : elements) {
                String abc = element.select(".c-gap-bottom-small a").text();
                System.out.println("abc：" + abc);
                String name2 = name.replace("电视剧","");
                System.out.println("hsdjkjklsk========================"+name2);
                Pattern pattern = Pattern.compile(name2 + "_百度百科");
                Matcher matcher = pattern.matcher(abc);
                if (matcher.find()) {
                    System.out.println("匹配ok");
                    String link = element.select(".c-gap-bottom-small a").attr("href");
                    System.out.println("link：" + link);
                    String html2 = gethtml(link);
                    Document doc2 = Jsoup.parse(html2);
                    Elements elementss = doc2.select(".name");
                    int i = 0;
                    String str = "||类 型：空";
                    String str2 = "||集 数：空";
                    String str3 = "||出品时间：空";
                    String str4 = "||出品公司：空";
                    String str5 = "||拍摄地点：空";
                    String str6 = "||发行公司：空";
                    String str7 = "||导演：空";
                    String str8 = "||首播时间：空";
                    String str9 = "||编剧：空";
                    String str10 = "||主演：空";
                    String str11 = "||每集长度：空";
                    String str12 = "||制片人：空";
                    String str13 = "||在线播放平台：空";
                    String str14 = "||首播平台：空";
                    String str15 = "||原著：空";
                    String str16 = "||监制：空";
                    String str17 = "||主要奖项：空";
                    String str18 = "||制片地区：空";
                    String str19 = "||中文名：空";
                    for (Element dls : elementss) {
                        String key = dls.text();
                        System.out.println("key：" + key);
                        if (key.equals("中文名")) {
                            str19 = "||";
                            str19 += "中文名";
                            str19 += "：";
                            str19 += doc2.select(".value").get(i).text();
                         }
                        if (key.equals("类 型")) {
                            str = "||";
                            str += "类 型";
                            str += "：";
                            str += doc2.select(".value").get(i).text();
                         }
                        if (key.equals("集 数")) {
                            str2 = "||";
                            str2 += "集 数";
                            str2 += "：";
                            str2 += doc2.select(".value").get(i).text();
                            
                         }
                        if(key.equals("出品时间")) {
                            str3 = "||";
                            str3 += "出品时间";
                            str3 += "：";
                            str3 += doc2.select(".value").get(i).text();
                         }
                        if(key.equals("出品公司")) {
                            str4 = "||";
                            str4 += "出品公司";
                            str4 += "：";
                            str4 += doc2.select(".value").get(i).text();
                         }
                        if(key.equals("拍摄地点")) {
                            str5 = "||";
                            str5 += "拍摄地点";
                            str5 += "：";
                            str5 += doc2.select(".value").get(i).text();
                         }if(key.equals("发行公司")) {
                            str6 = "||";
                            str6 += "发行公司";
                            str6 += "：";
                            str6 += doc2.select(".value").get(i).text();
                        }if(key.equals("导 演")) {
                            str7 = "||";
                            str7 += "导 演";
                            str7 += "：";
                            str7 += doc2.select(".value").get(i).text();
                        }if(key.equals("首播时间")) {
                            str8 = "||";
                            str8 += "首播时间";
                            str8 += "：";
                            str8 += doc2.select(".value").get(i).text();
                        }if(key.equals("编 剧")) {
                            str9 = "||";
                            str9 += "编 剧";
                            str9 += "：";
                            str9 += doc2.select(".value").get(i).text();
                        }if(key.equals("主 演")) {
                            str10 = "||";
                            str10 += "主 演";
                            str10 += "：";
                            str10 += doc2.select(".value").get(i).text();
                        }if(key.equals("每集长度")) {
                            str11 = "||";
                            str11 += "每集长度";
                            str11 += "：";
                            str11 += doc2.select(".value").get(i).text();
                        }if(key.equals("制片人")) {
                            str12 = "||";
                            str12 += "制片人";
                            str12 += "：";
                            str12 += doc2.select(".value").get(i).text();
                        }if(key.equals("在线播放平台")) {
                            str13 = "||";
                            str13 += "在线播放平台";
                            str13 += "：";
                            str13 += doc2.select(".value").get(i).text();
                        }if(key.equals("首播平台")) {
                            str14 = "||";
                            str14 += "首播平台";
                            str14 += "：";
                            str14 += doc2.select(".value").get(i).text();
                        }if(key.equals("原 著")) {
                            str15 = "||";
                            str15 += "原 著";
                            str15 += "：";
                            str15 += doc2.select(".value").get(i).text();
                        }if(key.equals("监 制")) {
                            str16 = "||";
                            str16 += "监 制";
                            str16 += "：";
                            str16 += doc2.select(".value").get(i).text();
                        }if(key.equals("主要奖项")) {
                            str17 = "||";
                            str17 += "主要奖项";
                            str17 += "：";
                            str17 += doc2.select(".value").get(i).text();
                        }
                        if(key.equals("制片地区")) {
                            str18 = "||";
                            str18 += "制片地区";
                            str18 += "：";
                            str18 += doc2.select(".value").get(i).text();
                        }

                        i++;
                    }
                    contentToTxt("d://bdbk/bdbkbdbk2.txt", name+"："+ str19+str+str2+str3+str4+str5+str6
                        +str7+str8+str9+str10+str11+str12+str13+str14+str15+str16+str17+str18);
                }

            }
        }
            //Elements elements = doc.select(".name");
            /*int i = 0;
            String str = "";
            for (Element dls : elements) {

                String key = dls.text();

                //System.out.println(key);
                if(key.equals("类 型")){
                    str = doc.select(".value").get(i).text();
                    System.out.println("类型："+str);
                }

                i++;
            }
            //contentToTxt("d://bdbk/bdbkbdbk2.txt", "百科："+str);*/
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
     * url编码
     * */
    /*public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }*/
    /*
     * 解码
     * */
    /*public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }*/
    /*
     * 按行读取数据
     * */
    public static List<String> txt2String(File file) {
        List<String> list = new ArrayList<String>();
        // StringBuilder result = new StringBuilder();
        try {
            //BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            
            
            InputStreamReader br = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader read = new BufferedReader(br);
            String s = null;
            while ((s = read.readLine()) != null) {
                list.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /*
     * get请求html页面
     * */
    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "BAIDUID=24BF48DC200EFFFE195943C82AF5FA86:FG=1; BIDUPSID=24BF48DC200EFFFE195943C82AF5FA86; PSTM=1548149958; Hm_lvt_55b574651fcae74b0a9f1cf9c8d7c93a=1548644245,1548741272,1548905704,1548983733; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDSFRCVID=AbtsJeCCxG3JhsQ9DA4J-rmjbjy8O-bEthyW3J; H_BDCLCKID_SF=JJ-D_IDbtDvjDbTnMnbqKRK8qxOKbJ3e5C6H3bP8aJ5MbpTLqfnkbfJBDGtfyM5Rtb5y5CKbWDQKMM5T-tooh6-7yajK2hJJbnRE54bFH4PWHRjshM7pQT8r5-FOK5Oib4DHLR3Pab3vOUjzXpO1Lj0zBN5thURB2DkO-4bCWJ5TMl5jDh05y6TLjNujJ5Ftf5vfL5rHan7_eTrnhPF3qt00KP6-3MJO3b7Z5qn8flbSfbL42Jt2MKuD5P6CK4QH22KeohFLtC0BhK-6D5RMK4_SbeItJ4TyKto2WbCQ0RoMqpcNLUbWQTtpMhbIqx3CJgLLQJ6_KJcBbhAz-xoMMbFiDPCE5bj2qRCfVC8a3J; delPer=0; PSINO=1; ZD_ENTRY=baidu; pgv_pvi=5970159616; pgv_si=s9487728640; BK_SEARCHLOG=%7B%22key%22%3A%5B%22%E5%9C%B0%E9%9B%B7%E8%8B%B1%E9%9B%84%E4%BC%A0%22%2C%22%E5%A4%A7%E6%B1%9F%E5%A4%A7%E6%B2%B3%22%2C%22%E5%8A%A8%E6%BC%AB%E8%8B%B1%E9%9B%84%22%2C%22%E8%8A%B1%E7%81%AB%22%5D%7D; Hm_lpvt_55b574651fcae74b0a9f1cf9c8d7c93a=1550030671; H_PS_PSSID=26525_1448_21124_26350_28414");
        request.setHeader("Host", "baike.baidu.com");
        request.setHeader("Referer","https://baike.baidu.com/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("Upgrade-Insecure-Requests", "1");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }
}
