package com.example.demo.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Xiecheng {

    public static void main(String[] args) throws IOException, ParseException {
//        File file = new File("D://xiecheng/携程景点.json");
//        List<String> list= SohuFangchanList.txt2String(file);

//        for(String url:list){

        String url = "http://you.ctrip.com/sight/huangshan19/64844.html";
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.10 Safari/537.36")
                    .header("Host","you.ctrip.com").
                            header("Upgrade-Insecure-Requests","1").get();
            String html = doc.html();

            String poiid = getStrByPrePost(html, "var poiid = \"", "\";");
            String resourceid = getStrByPrePost(html, "var resourceid = \"", "\";");
            String resourcetype = getStrByPrePost(html, "var resourcetype = \"", "\";");
            String districtid = getStrByPrePost(html, "var districtid = \"", "\";");
            String districtename = getStrByPrePost(html, "var districtename = \"", "\";");
            String star = getStrByPrePost(html, "var star = \"", "\";");
            String tourist = getStrByPrePost(html, "var tourist = \"", "\";");


            JSONObject jsonObject = returnXCJson(url, poiid, resourceid, resourcetype, districtid, districtename, star, tourist);
            contentToTxt("d://xiecheng/20190103.json", jsonObject.toJSONString());
//        }
    }

    public static void contentToTxt(String filePath, String content) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath),true));
            writer.write("\n"+content);
            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JSONObject returnXCJson(String url, String poiid, String resourceid, String resourcetype, String districtid, String districtename, String star, String tourist) throws IOException, ParseException {
        JSONObject jsonObject=new JSONObject();
        JSONObject reviewJson=new JSONObject();
        Document doc= Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.10 Safari/537.36")
                .header("Host","you.ctrip.com").get();
        Elements daohang=doc.select(".breadbar_v1.cf ul>li .icon_gt");

        String title=doc.select(".dest_toptitle.detail_tt h1").text();
        String score=doc.select(".detailtop_r_info .score").text();
        String comment_ring_16=doc.select(".comment_ring_16 span").attr("style").replace("width: ","");
        String dianpingCNT=doc.select("#hrefyyDp").text();
        String lizhuan=doc.select(".c_tipswrap .icon_c_tips").text();
        String cate="";
        for(Element e:daohang){
            cate+=e.previousElementSibling().text()+">";
        }
        cate+=title;
        jsonObject.put("OBJ_URL",url);
        jsonObject.put("OBJ_TIT", title);
        jsonObject.put("OBJ_PATH", cate);
        reviewJson.put("OBJ_TOTSCORE", score);
        reviewJson.put("OBJ_TOTSTAR", comment_ring_16);
        reviewJson.put("OBJ_REV_NUM", dianpingCNT);
        jsonObject.put("OBJ_INCENT", lizhuan);

        JSONArray menPiao=new JSONArray();
        if(doc.select(".play_together_list.play_tickects.cf").size()>0){
            Element table=doc.select(".play_together_list.play_tickects.cf").get(0);
            Elements trs=table.select("tr");


            if(trs.size()>0){
                trs.remove(0);
            }
            for(int i=0;i<trs.size();i++){
                Element tr=trs.get(i);
                JSONObject trObj=new JSONObject();
                trObj.put("SKU_NUM",i+1);
                Elements tds=tr.select("td");
                for(Element td:tds){
                    if(td.hasClass("one")){
                        trObj.put("SKU_TIT",td.text());
                    }else if(td.hasClass("two")){
                        trObj.put("SKU_NORMALPRICE",td.text().replace("¥",""));
                    }else if(td.hasClass("three")){
                        trObj.put("SKU_SALEPRICE",td.text().replace("¥",""));
                    }
//                    else{
//                        String href=td.select("a").attr("href");
//                        System.out.println("+++++++++++++"+href);
//                        Document tdDoc=Jsoup.connect(href).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3610.2 Safari/537.36").get();
//                        if(tdDoc.select(".month_sale em").size()>0){
//                            trObj.put("月销",tdDoc.select(".month_sale em").text());
//                        }else if(tdDoc.select(".saleInfo p.text:contains(月销)").size()>0){
//                            trObj.put("月销",tdDoc.select(".saleInfo p.text:contains(月销)").first().previousElementSibling().text());
//                        }else{
//                            trObj.put("月销","");
//                        }
//
//                    }

                }
                menPiao.add(trObj);
            }
        }


        jsonObject.put("menPiaoList", menPiao);

        doc.select(".detailcon.bright_spot li i").empty();
        String detailcon_bright_spot=doc.select(".detailcon.bright_spot li").text();
        jsonObject.put("OBJ_SPOTLIGHT", detailcon_bright_spot);//亮点

        Elements jdjs=doc.select(".detailcon.detailbox_dashed");
        for(Element jd:jdjs){
            if(jd.select(">h2").text().equals("景点介绍")){
                Elements ps=jd.select(".toggle_l .text_style p");
                jsonObject.put("OBJ_INTRO", ps.text());
//                String[] psArry=ps.text().replaceAll("。 ", "。").split(" ");
//                for(String s:psArry) {
//                }


//                String introduction_scenic_spots=ps.get(0).text();//景点介绍
//                jjdp.put("景点介绍", introduction_scenic_spots);
//                for(Element p:ps){
//                    if(p.select(":has(strong)").size()>0){
//                        String tit=p.select(":has(strong)").text();
//                        String content=p.select(":has(strong)").get(0).nextElementSibling().text();
//                        jjdp.put(tit, content);
//                    }
//                }

            }
        }

        Elements dianpingshu=doc.select(".comment_show dd");
        JSONObject yydp=new JSONObject();
        for(Element dp:dianpingshu){
            String l_title=dp.select(".l_title").text();
            String l_score=dp.select(".score").text();
            yydp.put(l_title, l_score);
        }
        reviewJson.put("OBJ_REV_SUBSCORE", yydp);

        JSONObject bgnormalJson=new JSONObject();
        Elements cate_count=doc.select(".cate_count li");
        for(Element c:cate_count){
            bgnormalJson.put(c.select(".bgnormal").text(), c.select(".ct_count").text());
        }
        jsonObject.put("OBJ_REV_CAT_NUM", bgnormalJson);

        String tablist=doc.select(".detailtab.cf .tablist li a").text();
        reviewJson.put("OBJ_REV_INTRO", tablist);//全部（11903）很好（8700）

        String id=url.substring(url.lastIndexOf("/")+1,url.indexOf(".html"));
        String times=showGowantAjax(id,url);
        if(StringUtils.isNotEmpty(times)){
            System.out.println(times);
            JSONObject ts= (JSONObject) JSONObject.parse(times);
            Integer wentTimes=ts.getInteger("WentTimes");
            Integer wantTimes=ts.getInteger("WantTimes");


            JSONObject OBJ_POP=new JSONObject();

            OBJ_POP.put("想去",wentTimes);
            OBJ_POP.put("去过",wantTimes);
            jsonObject.put("OBJ_POP",OBJ_POP);
        }



        JSONArray reviewJSONARRY=new JSONArray();


        int i=1;
        while (true){
//            if(i==248){
//                break;
//            }
            if(i==302){
                break;
            }
            System.out.println("truePageNum="+i);
            Document d=null;
            try{
                d=Jsoup.parse(AsynCommentView(i+"",poiid,resourceid,resourcetype,districtid,districtename,star,tourist,url));
            }catch (Exception e1){
                System.out.println("pageNum-----"+i);
                System.exit(0);
            }

            if(!d.select(".comment_ctrip p.aligncenter").isEmpty()){
                break;
            }
            reviewJSONARRY.addAll(getReviewJSON(d));
            i++;
        }

        System.out.println("reviewJSONARRY.size()----"+reviewJSONARRY.size());
        reviewJson.put("reviewArry", reviewJSONARRY);
        jsonObject.put("reviewJson", reviewJson);

        return jsonObject;
    }


    public static JSONArray getReviewJSON(Document doc) throws IOException, ParseException {
        JSONArray reviewArry=new JSONArray();
        Date a=new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-31");
        Date b=new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01");

        Elements reviews=doc.select(".comment_single");
        for(Element review:reviews) {
            String datePublished=review.select("li.from_link .f_left .time_line").text();

            Date c = new SimpleDateFormat("yyyy-MM-dd").parse(datePublished);

            if (c.before(b) && c.after(a)) {
                JSONObject reviewObj=new JSONObject();

                String star=review.select("ul li.title.cf .f_left .starlist span").attr("style").replace("width:", "")
                        .replace(";", "");
                String sblockline=review.select("ul li.title.cf .f_left .sblockline").text();

                String main_con=review.select(".main_con .heightbox").text();
                Elements imgs=review.select(".comment_piclist.cf a");
                int imgCnt=imgs.size();
                List<String> imgList=new ArrayList<String>();
                for(Element img:imgs) {
                    imgList.add(img.attr("href"));
                }
                reviewObj.put("REV_PIC_URL", imgList);

                String useful=review.select("li.from_link .f_right .useful em").text();
                reviewObj.put("REV_STAR",star);
                reviewObj.put("REV_INTRO",executeStringToJSONObject(sblockline));
                reviewObj.put("REV_CONTENT",main_con);
                reviewObj.put("REV_PIC_NUM",imgCnt);
                reviewObj.put("REV_TIME",datePublished);
                reviewObj.put("REV_PR_NUM",useful);

                String userHref="http://you.ctrip.com"+review.select(".userimg a").attr("href");
                Document user=null;
                try{
                    user=Jsoup.connect(userHref).get();
                }catch (Exception e1){
                    try{
                        user=Jsoup.connect(userHref).get();
                    }catch (Exception e2){
                        try{
                            user=Jsoup.connect(userHref).get();
                        }catch (Exception e3){
                            try{
                                user=Jsoup.connect(userHref).get();
                            }catch (Exception e4){
                                try{
                                    user=Jsoup.connect(userHref).get();
                                }catch (Exception e5){
                                    try{
                                        user=Jsoup.connect(userHref).get();
                                    }catch (Exception e6){
                                        try{
                                            user=Jsoup.connect(userHref).get();
                                        }catch (Exception e7){
                                            try{
                                                user=Jsoup.connect(userHref).get();
                                            }catch (Exception e8){
                                                System.out.println("userHref----------------------------------"+userHref);
                                                System.exit(0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                String info_name=user.select(".info-main .item>span.info-name").text();
                String info_level=user.select(".info-main .item>a .info-level").text();

                JSONObject userJson=new JSONObject();

                String sex=user.select(".info-main .item.gray .J_gender i").attr("title");
                userJson.put("U_HREF",userHref);
                userJson.put("U_NICKNAME", info_name);
                userJson.put("U_LEVEL", info_level);
                userJson.put("U_SEX", sex);

                user.select(".info-main .item.gray span.J_gender").remove();

                userJson.put("U_ENTERTIME", user.select(".info-main .item.gray span").text());
                reviewObj.put("userJson", userJson);
                reviewArry.add(reviewObj);
            }

            if(!c.after(a)){
                break;
            }




        }
        return reviewArry;
    }

    public static JSONObject executeStringToJSONObject(String str){
        JSONObject properties = new JSONObject();
        String[] descArray = str.split("\t");
        if (str.indexOf("\t") >= 0) {
            descArray = str.split("\t");
        } else {
            descArray = str.split(" ");
        }
        String[] temp = null;
        for (int i = 0; i < descArray.length; i++) {
            if (descArray[i].indexOf(":") == 0
                    || descArray[i].indexOf("：") == 0) {
                descArray[i] = descArray[i].substring(1);
            }
            if (descArray[i].indexOf(": ") >= 0) {
                temp = descArray[i].split(": ");
            } else if (descArray[i].indexOf("： ") >= 0) {
                temp = descArray[i].split("： ");
            } else if (descArray[i].indexOf("：") > 0) {
                temp = descArray[i].split("：");
            } else {
                temp = descArray[i].split(" ");
            }
            if (temp.length < 2) {
                continue;
            }
            if (temp.length > 2) {
                String value = "";
                for (int j = 1; j < temp.length; j++) {
                    value += temp[j] + " ";
                }
                temp[1] = value;
            }
            temp[0] = temp[0].replaceAll("：", "").replace(":", "")
                    .replace(" ", "").trim();
            properties.put(temp[0], temp[1]);
        }
        return properties;
    }

    public static String AsynCommentView(String pageNo,String poiid, String resourceid, String resourcetype, String districtid, String districtename, String star, String tourist,String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post =new HttpPost("http://you.ctrip.com/destinationsite/TTDSecond/SharedView/AsynCommentView");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("poiID",poiid));
        param.add(new BasicNameValuePair("districtId",districtid));
        param.add(new BasicNameValuePair("districtEName",districtename));
        param.add(new BasicNameValuePair("pagenow",pageNo));

        param.add(new BasicNameValuePair("order","1"));
        param.add(new BasicNameValuePair("star",star));
        param.add(new BasicNameValuePair("tourist",tourist));
        param.add(new BasicNameValuePair("resourceId",resourceid));
        param.add(new BasicNameValuePair("resourcetype",resourcetype));

        post.setHeader("Accept","*/*");
        post.setHeader("Accept-Encoding","gzip, deflate");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Connection","keep-alive");
        post.setHeader("Content-Type","application/x-www-form-urlencoded");
        post.setHeader("Cookie","ASP.NET_SessionSvc=MTAuOC4xODkuNTd8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTUzNzQzMjI0MDA0OQ; _bfa=1.1542962752777.40ezl5.1.1542962752777.1542962752777.1.1; _bfs=1.1; gad_city=5f1e9e5c1eb7fae5024f3f587fa07e91; bdshare_firstime=1542962753433; MKT_Pagesource=PC; appFloatCnt=1; _ga=GA1.2.1190615081.1542962760; _gid=GA1.2.1937028577.1542962760; _gat=1; _jzqco=%7C%7C%7C%7C%7C1.1040970466.1542962759617.1542962759617.1542962759617.1542962759617.1542962759617.0.0.0.1.1; __zpspc=9.1.1542962759.1542962759.1%234%7C%7C%7C%7C%7C%23");
        post.setHeader("Host","you.ctrip.com");
        post.setHeader("Origin","https://you.ctrip.com");
        post.setHeader("Referer",url);
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");
        post.setHeader("X-Requested-With","XMLHttpRequest");

        post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
        CloseableHttpResponse response =httpClient.execute(post);

        int statusCode =response.getStatusLine().getStatusCode();
        HttpEntity entity =response.getEntity();
        String html = EntityUtils.toString(entity,"utf-8");
        response.close();
        httpClient.close();

        return html;
    }

    public static String showGowantAjax(String id,String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post =new HttpPost("http://you.ctrip.com/Destinationsite/SharedComm/ShowGowant");
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("Resource",id));
        param.add(new BasicNameValuePair("pageType","Sight"));

        post.setHeader("Accept","*/*");
        post.setHeader("Accept-Encoding","gzip, deflate");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Connection","keep-alive");
        post.setHeader("Content-Type","application/x-www-form-urlencoded");
        post.setHeader("Cookie","ASP.NET_SessionSvc=MTAuOC4xODkuNjF8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTUyNjU0OTk3OTY0MQ; _bfa=1.1530278874215.begji.1.1530278874215.1530278874215.1.1; _bfs=1.1; adscityen=Shenyang; bdshare_firstime=1530278874544; _RF1=113.225.131.1; _RSG=ukaiLDY.CRCuKVmgIp25dB; _RDG=28a98c4ff6a177219d29b9eb83b3263b1f; _RGUID=c6ccfa23-55b2-4bb8-b9c5-0b34aac1f538; __zpspc=9.1.1530278876.1530278876.1%234%7C%7C%7C%7C%7C%23; _jzqco=%7C%7C%7C%7C1530278877095%7C1.1280466730.1530278876990.1530278876990.1530278876990.1530278876990.1530278876990.0.0.0.1.1; MKT_Pagesource=PC; appFloatCnt=1; _ga=GA1.2.204053816.1530278877; _gid=GA1.2.2143442865.1530278877; _bfi=p1%3D290510%26p2%3D0%26v1%3D1%26v2%3D0; manualclose=1");
        post.setHeader("Host","you.ctrip.com");
        post.setHeader("Origin","https://you.ctrip.com");
        post.setHeader("Referer",url);
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");
        post.setHeader("X-Requested-With","XMLHttpRequest");

        post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
        CloseableHttpResponse response =httpClient.execute(post);
        int statusCode =response.getStatusLine().getStatusCode();
        HttpEntity entity =response.getEntity();
        String html = EntityUtils.toString(entity,"utf-8");
        response.close();
        httpClient.close();
        return html;
    }

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

}
