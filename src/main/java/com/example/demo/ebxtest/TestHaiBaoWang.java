package com.example.demo.ebxtest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestHaiBaoWang {


    public static void main(String[] args) {
        try {
            String str = AsynCommentView("2466704");
            //System.out.println(str);

            Document doc = Jsoup.parse(str);
            Elements elements = doc.select(".comment-item");
            for(Element element:elements){
                String name = element.select(".name a").text();
                String name2 = element.select(".name").text();
                name2 = name2.substring(0,name2.indexOf(" "));
                if(name.isEmpty()){
                    System.out.println( name2+"!!!!!!!!!!!!!!!!!!!!!");


                }else {
                    System.out.println(name+"===============");

                }
                String up_cnt = "";
                if(!("".equals(element.tagName(".returnL3")))){
                    up_cnt = element.select(".returnL3").get(0).text();
                }
                System.out.println("======"+up_cnt);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //post请求
    public static String AsynCommentView(String objectId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /*objectId: 2057322
        page: 2
        type: ARTICLE
        pageCommentId: 4929316
        typeList: 0*/
        //请求url
        HttpPost post =new HttpPost("http://comments.haibao.com/ajax/comment:loadCommentMore.html");
        System.out.println("123");
        List<NameValuePair> param = new ArrayList<>();

        param.add(new BasicNameValuePair("objectId",objectId));
        param.add(new BasicNameValuePair("page","1"));
        param.add(new BasicNameValuePair("type","ARTICLE"));
        //param.add(new BasicNameValuePair("pageCommentId","4952983"));
        param.add(new BasicNameValuePair("typeList","0"));

        post.setHeader("Accept","*/*");
        post.setHeader("Accept-Encoding","gzip, deflate");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Connection","keep-alive");

        post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//        post.setHeader("Cookie","hbUnregUserId=09AB63DB-02CB-40BD-9CBC-5A097780DABA; Hm_lvt_9448a813e61ee0a7a19c41713774f842=1547546833,1547607514,1547789391,1548122299; Hm_lvt_06ffaa048d29179add59c40fd5ca41f0=1547546832,1547607514,1547789391,1548122299; Hm_lvt_793a7d1dd685f0ec4bd2b50e47f13a15=1547546833,1547607514,1547789391,1548122299; __captcha_comment=zrzdWwaMTDrPQnc4WKbeijw9SzD74v7PpwDlcE9YJz8%3D; Hm_lpvt_06ffaa048d29179add59c40fd5ca41f0=1548125072; Hm_lpvt_9448a813e61ee0a7a19c41713774f842=1548125072; Hm_lpvt_793a7d1dd685f0ec4bd2b50e47f13a15=1548125072");
        //post.setHeader("Content-Length","69");
        post.setHeader("Host","comments.haibao.com");
        post.setHeader("Origin","http://comments.haibao.com");
        post.setHeader("Referer","http://comments.haibao.com/comments/"+objectId+"/ARTICLE/");

        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
//        post.setHeader("X-Requested-With","XMLHttpRequest");

        post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
        CloseableHttpResponse response =httpClient.execute(post);
        //int statusCode =response.getStatusLine().getStatusCode();
        HttpEntity entity =response.getEntity();
        String posthtml = EntityUtils.toString(entity,"utf-8");
        response.close();
        httpClient.close();
        //System.out.println("post请求"+posthtml);

        return posthtml;
    }
}
