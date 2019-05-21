package com.example.demo.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class longge666 {
    public static void main(String[] args) {
        String hhh =     gethtml("https://v.qq.com/detail/s/s3vv70iofg1pwre.html");
        System.out.println("============="+hhh);
    }

//accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//accept-encoding: gzip, deflate, br
//accept-language: zh-CN,zh;q=0.9
//cache-control: max-age=0
//cookie: pgv_pvid=8545944658; tvfe_boss_uuid=2739e2fcd409283b; ts_uid=2213573880; tvfe_search_uid=8638da48-cdef-4944-a0ba-02cef7a09d6b; bucket_id=9231009; pac_uid=0_32906de2df684; pgv_pvi=775371776; pgv_info=ssid=s8423141374; pgv_si=s473338880; ts_last=v.qq.com/detail/s/s3vv70iofg1pwre.html; ts_refer=www.baidu.com/link; ptag=www_baidu_com; ad_play_index=103; qv_als=ZyjwXbNvcXwUJInFA11552464427sSdiIg==
//if-modified-since: Wed, 13 Mar 2019 08:00:00 GMT
//upgrade-insecure-requests: 1
//user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36    //




    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.setHeader("accept-encoding", "gzip, deflate, br");
        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
        request.setHeader("cache-control", "max-age=0");
        request.setHeader("upgrade-insecure-requests", "1");
        request.setHeader("if-modified-since","Wed, 13 Mar 2019 08:00:00 GMT");
        request.setHeader("user-agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("cookie", "pgv_pvid=8545944658; tvfe_boss_uuid=2739e2fcd409283b; ts_uid=2213573880; tvfe_search_uid=8638da48-cdef-4944-a0ba-02cef7a09d6b; bucket_id=9231009; pac_uid=0_32906de2df684; pgv_pvi=775371776; pgv_info=ssid=s8423141374; pgv_si=s473338880; ts_last=v.qq.com/detail/s/s3vv70iofg1pwre.html; ts_refer=www.baidu.com/link; ptag=www_baidu_com; ad_play_index=103; qv_als=ZyjwXbNvcXwUJInFA11552464427sSdiIg==");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }

}
