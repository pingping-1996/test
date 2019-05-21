package com.example.demo.media_news;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class yangguangwang {
    public static void main(String[] args) throws HttpProcessException {
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                //.cookie("_Xdwuv=5541698168407;_fwck_www=95764b86a92687ad4d0a2f95cdfbd288;_appuv_www=7462bd0c27b7d89d6dd768226d5d29e9")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)  //设置headers，不需要时则无需设置
                .url("http://news.cnr.cn/local/").encoding("GBK"));
        //System.out.println(html);
        Document doc = Jsoup.parse(html);

       // if(url.equals("1")){
            Elements elements = doc.select(".wh581.margin h3 a");
            int i = 1;
            for (Element element: elements) {
                System.out.println("i==="+i);
                String title = element.text();
                String href = element.attr("href");
                System.out.println("title===123"+title+href);
                i++;


                String lastpage = "";
                String pattern = "createPageHTML\\((\\d+),";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(html);
                if(m.find()){
                    lastpage = m.group(1);
                    Integer lastpage2 = Integer.valueOf(lastpage);
                    System.out.println(lastpage2);

                }

            }
       // }
        //if(url.equals("")){
            Elements elements2 = doc.select(".erji_left li span.bt");
            int i2 = 1;
            for (Element element: elements2) {
                System.out.println("i2==="+i2);
                String title = element.text();
                String href = element.attr("href");
                System.out.println("title===123"+title+href);
                i2++;
            }

        Elements elements3 = doc.select(".detail div h2 a");
        int i3 = 1;
        for (Element element: elements3) {
            System.out.println("i3==="+i3);
            String title = element.text();
            String href = element.attr("href");
            System.out.println("title===123"+title+href);
            i3++;



        }
/*.articleList  li .text  strong a*/
//        }

        Elements elements4 = doc.select(".articleList  li .text  strong a");
        int i4 = 1;
        for (Element element: elements4) {
            System.out.println("i4==="+i4);
            String title = element.text();
            String href = element.attr("href");
            System.out.println("title===123"+title+href);
            i4++;
        }


        String json = html.substring(html.indexOf("var collection ="),html.indexOf("];"));
        String json2 = json.replace("var collection = ","")+"]";
        System.out.println("666==="+json2);
        System.out.println(json2.split("}").length);
        String[] arry = json2.split("}");
        for(int j = 0;j<arry.length-1;j++){
            String index = arry[j];
            String title = index.substring(index.indexOf("title: \""),index.indexOf("content: "));
            String title2 = title.replace("title: \"","")
                    .replace("\",","");
            String url = index.substring(index.indexOf("url: \""),index.indexOf("shtml"));
            String url2 = url.replace("url: \"","")+"shtml";
            System.out.println(j+"==="+title2+"==="+url2);

        }


        //var collection =
        // ;
        //		</script>



    }

}
