package com.example.demo.tengxun;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XLXW {
    public static void main(String[] args) throws HttpProcessException, ParseException {

        String aboutpage = "http://cmnt.sina.cn/aj/v2/list?channel=gn&newsid=comos-hvhiqax1431711&group=0&thread=1&page=1";
        int page = 0;

        for(int k = 0;k<16;k++){

        String pattern = "page=(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(aboutpage);
        if(m.find()){
            page = Integer.valueOf(m.group(1));
            System.out.println("匹配==="+m.group(1));
        }

        Header[] headers = HttpHeader.custom()
                //.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .referer("http://cmnt.sina.cn/index?product=comos&index=hvhiqax1431711&tj_ch=news&is_clear=")
                //.cookie("ustat=__10.79.112.153_1555410691_0.03596800; genTime=1555410691; SINAGLOBAL=1177853010294.7004.1555412574067; Apache=3330015996086.619.1557797898762; ULV=1557797898763:2:1:1:3330015996086.619.1557797898762:1555412574067; vt=4; historyRecord={\"href\":\"http://cmnt.sina.cn/index\",\"refer\":\"\"}")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)  //设置headers，不需要时则无需设置
                .url(aboutpage).encoding("utf-8"));
        System.out.println(html);

        //String html2 = html.replace("jsonp1(","").replace(");","");
        JSONObject jsonObject = new JSONObject();
        List list = new ArrayList();
        JSONObject json = JSONObject.parseObject(html);
        JSONArray arr = json.getJSONObject("result").getJSONArray("cmntlist");
        for (int i=0;i<arr.size();i++) {
            JSONObject json1 = arr.getJSONObject(i);
            String content = json1.getString("content");
            String time = json1.getString("time");
            String name = json1.getJSONObject("_config").getString("wb_screen_name");
            String up_cnt = json1.getString("agree");
            int up_cnt_2 = Integer.valueOf(up_cnt);

            Map map = new HashMap();
            map.put("username",name);
            map.put("comment_time",time);
            map.put("comment_content",content);
            map.put("up_cnt",up_cnt_2);
            list.add(map);
        }
        String url = "http://news.sina.cn/2019-04-10/detail-ihvhiqax1431711.d.html?sinawapsharesource\\u003dnewsapp";
        String title  = "山东茌平县委书记张琳自杀身亡 亲属称其有抑郁症";
        String keyword = "山东茌平县委书记张琳昨晚自杀身亡 患有抑郁症";
        jsonObject.put("url",url);
        jsonObject.put("title",title);
        jsonObject.put("url",url);
        jsonObject.put("keyword",keyword);
        jsonObject.put("comments",list);
        System.out.println(jsonObject);
        contentToTxt("C:\\Users\\BFD-LT\\Desktop\\新浪新闻.txt","第"+page+"条==="+jsonObject);
        String page2 = String.valueOf(page+1);
        aboutpage = aboutpage.replace("page="+m.group(1),"page="+page2);
    }
    }
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
