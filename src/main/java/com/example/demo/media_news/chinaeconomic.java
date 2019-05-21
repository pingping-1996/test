package com.example.demo.media_news;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chinaeconomic {
    public static void main(String[] args) throws HttpProcessException {
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                //.cookie("_Xdwuv=5541698168407;_fwck_www=95764b86a92687ad4d0a2f95cdfbd288;_appuv_www=7462bd0c27b7d89d6dd768226d5d29e9")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)  //设置headers，不需要时则无需设置
                .url("http://tuopin.ce.cn/rdht/index_6.shtml").encoding("GBK"));
        Document doc = Jsoup.parse(html);
        //System.out.println(html);
        String pattern = "createPageHTML\\((\\d+)\\,";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(html);
        String last = "";
        if (m.find()){
            last = m.group(1);
        }
        //createPageHTML((\\d+),
        System.out.println("========="+last);


        String url = "http://tuopin.ce.cn/rdht/index_6.shtml";
//如果截取到、或者截取到为首页
        String index = "index_(\\d+).shtml";
        Pattern r2 = Pattern.compile(index);
        Matcher m2 = r2.matcher(url);
        String yema = "";
        String nextpage = "";
        if(m2.find()){
            yema = m2.group(1);
            String yema2 = m2.group();
            System.out.println(yema+yema2);
        }else{
            nextpage = url + "index_2.shtml";
            System.out.println(yema+nextpage);

        }
    }
}
