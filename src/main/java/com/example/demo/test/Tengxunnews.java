package com.example.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.hadoop.util.LimitInputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.misc.DoubleConsts;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tengxunnews {

    public static void main(String[] args) {
        //http://coral.qq.com/article/3763570682/comment/v2?callback=_article3763570682commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1
        //http://coral.qq.com/article/3763656988/comment/v2?callback=_article3763656988commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1&_=1551950365995
        //http://coral.qq.com/comment/6509100129864673117/reply/v2?callback=_comment6509100129864673117replyv2&targetid=3764469063&reqnum=10&pageflag=2&source=1
        //http://coral.qq.com/article/3764469063/comment/v2?callback=_article3764469063commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6509213987263871496&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1
        //http://coral.qq.com/article/3764383888/comment/v2?callback=_article3764383888commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1
        //http://coral.qq.com/article/3764469063/comment/v2?callback=_article3764469063commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1
                    //http://coral.qq.com/article/3764469063/comment/v2?callback=_article3764469063commentv2&orinum=10&oriorder=o&pageflag=1
        //String url = "http://coral.qq.com/article/3764469063/comment/v2?callback=_article3764469063commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1";
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508978510644507094
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508978510644507094

        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508944785043581961
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508944785043581961

        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508983903570951293
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508970915363880200
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508984321751351154
        //http://coral.qq.com/article/3764028740/comment/v2?callback=_article3764028740commentv2&orinum=10&oriorder=o&pageflag=1&cursor=6508988096096466220
        //String url = "https://openapi.inews.qq.com/getQQNewsNormalContent?id=20190306A0M77B00&chlid=news_rss&refer=mobilewwwqqcom&otype=jsonp&ext_data=all&srcfrom=newsapp&callback=getNewsContentOnlyOutput";
        try{
        //String url = "http://coral.qq.com/article/3767100746/comment/v2?callback=_article3767100746commentv2&orinum=10&oriorder=o&pageflag=1&cursor=0&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1";
        String url = "https://new.qq.com/omn/20190310/20190310A0YRGW.html";
        Map<String, Object> resultData =  new HashMap<>();
        List<Map<String, Object>> comments = new ArrayList<>();
        List<Map<String, Object>> tasks = new ArrayList<>();
        String arry =  gethtml(url);
        System.out.println(arry);
        /*String regex = "_article(\\d+)commentv2\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(arry);
        String xxx = "";
        if (matcher.find()) {
            xxx = matcher.group(0);
        }
        String html = arry.replace(xxx,"");
      String html2 = html.substring(0,html.length()-1);
        JSONObject jsonObject = JSON.parseObject(html2);
        JSONObject jsonObject2 = jsonObject.getJSONObject("data");
        System.out.println(jsonObject2);
        JSONArray jsonArray = jsonObject2.getJSONArray("oriCommList");
        System.out.println(jsonArray);

        String id = "";
        String userid = "";
        String name = "";
        for(int i=0;i<jsonArray.size();i++){
            List list = new ArrayList();
            //System.out.println("进入");
            JSONObject jsonArrays = jsonArray.getJSONObject(i);
            String content = jsonArrays.getString("content");//评论内容
            //System.out.println(content);

            String up = jsonArrays.getString("up");//点赞数
            //System.out.println(up);
            id = jsonArrays.getString("id");
            userid = jsonArrays.getString("userid");
            String rep = jsonObject2.getString("repCommList");
            System.out.println("rep==="+rep);
            JSONObject object = jsonObject2.getJSONObject("repCommList");
            if(!object.isEmpty()){
                JSONArray arryy = object.getJSONArray(id);
                if(arryy!=null){
                    for(int j=0;j<arryy.size();j++){
                        JSONObject reviewObj = new JSONObject();
                        JSONObject arryys = arryy.getJSONObject(j);
                        String content2 = arryys.getString("content");
                        String up2 = arryys.getString("up");
                        JSONObject object2 = jsonObject2.getJSONObject("userList");
                        userid = arryys.getString("userid");
                        if(!object2.isEmpty()){
                            JSONObject object3 =  object2.getJSONObject(userid);
                            name = object3.getString("nick");
                        }
                        System.out.println("二级content======"+content2);
                        reviewObj.put("content",content2);
                        reviewObj.put("up_cnt", up2);
                        reviewObj.put("username", name);
                        list.add(reviewObj);
                    }
                }
            }

            JSONObject object2 = jsonObject2.getJSONObject("userList");
            if(!object2.isEmpty()){
                JSONObject object3 =  object2.getJSONObject(userid);
                name = object3.getString("nick");
                //JSONArray arryy = object.getJSONArray(id);
                System.out.println("hjklklmm===="+name);

            }
            Map map = new HashMap();
            map.put("content",content);
            map.put("up_cnt",up);
            map.put("answers",list);
            map.put("username",name);
            comments.add(map);*/

            /*JSONArray mapid = object.getJSONArray(id);
            System.out.println(mapid);*/

            /*JSONObject arryys = new JSONObject();
                if(mapid!=null) {
                    for (int z = 0; z < mapid.size(); z++) {
                        arryys = mapid.getJSONObject(z);
                        String commmmm = arryys.getString("content");
                        String commmmm = arryys.getString("content");
                        String commmmm = arryys.getString("content");
                        String commmmm = arryys.getString("content");
                arryys = new JSONObject();
                arryys.put("content", content);
                System.out.println("hdashfklaskfklasjfkl" + commmmm);
                }
            }
            JSONArray mapid1 = new JSONArray();
            mapid1.add(arryys);
        }
            resultData.put("replys",comments);
            JSONObject jjj = new JSONObject(resultData);*/

         }catch (Exception e){
            e.printStackTrace();
        }
        /*String url = "https://new.qq.com/omn/20190306/20190306A0CTOD.html";
        String get = gethtml(url);
        System.out.println(get);
        //last 有下一页的id码
        Document doc = Jsoup.parse(get);
        String text = doc.select(".content-article p").text();
        System.out.println("文章内容============="+text);
        String regex = "\"comment_id\": \"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(get);
        if (matcher.find()) {
            System.out.println("id======" + matcher.group(1));
        }

        String regex2 = "\"title\": \"(.*?)\"";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(get);
        if (matcher2.find()) {
            System.out.println("title======" + matcher2.group(1));
        }

        String regex3 = "<p class=\"one-p\">作者：(.*?)";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(get);
        if (matcher3.find()) {
            System.out.println("作者======" + matcher3.group(1));
        }*/

        /*String html = get.replace("getNewsContentOnlyOutput(","");
        String html2 = html.substring(0,html.length()-1);
        JSONObject jsonObject = JSON.parseObject(html2);

        System.out.println(jsonObject.getString("title"));
        JSONArray jsonArray = jsonObject.getJSONArray("content");
        String value = "";
        List list = new ArrayList();
         for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonArrays=jsonArray.getJSONObject(i);
            value = jsonArrays.getString("value");
            //System.out.println(i+ value);
            list.add(value);
           }
        System.out.println(list);
        String strResult = "";
        for(int i=0;i<list.size();i++){
            strResult+=list.get(i);
        }
        //String strResult2 = strResult.substring(0,strResult.length());
        System.out.println("文章内容==="+strResult);*/


        /*JSONObject jsonObject = JSON.parseObject(get);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if(jsonArray.isEmpty()){
            System.out.println("最后一页");
        }
        for(int i=0;i<jsonArray.size();i++){
            JSONObject reviewObj = new JSONObject();
            JSONObject jsonArrays=jsonArray.getJSONObject(i);
            jsonArrays.getString("url");
            System.out.println(i+"=========="+jsonArrays.getString("url"));

        }
*/
         System.out.println(stampToDate("1552269574"));
    }


    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
    * public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    *
    * */
    //https://pacaio.match.qq.com/irs/rcd?cid=146&token=49cbb2154853ef1a74ff4e53723372ce&ext=msh&page=2
    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "*/*");
        request.setHeader("accept-encoding", "gzip, deflate, br");
        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
        //request.setHeader("Connection", "keep-alive");
        //request.setHeader("Host", "shankapi.ifeng.com");
        request.setHeader("referer","https://new.qq.com/ch/politics/");
//        request.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //request.setHeader("Cookie", "pgv_pvid=8545944658; tvfe_boss_uuid=2739e2fcd409283b; pac_uid=0_32906de2df684; pgv_info=ssid=s446121789; pgv_pvi=775371776; pgv_si=s807455744");
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
