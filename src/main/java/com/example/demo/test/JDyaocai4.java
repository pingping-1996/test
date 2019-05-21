package com.example.demo.test;



import com.example.demo.QiniuAgent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;

public class JDyaocai4 {

    public static void main(String[] args) throws SQLException {
        try {
            QiniuAgent qiniuAgent = new QiniuAgent();
            String html = qiniuAgent.doGetRequest("100002539304",1);
            //System.out.println("JD==="+html);
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select(".gl-item");
            int i = 1;
            for (Element dls : elements) {
                String url = dls.select(".p-img a").attr("href");
                System.out.println(i + url);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
