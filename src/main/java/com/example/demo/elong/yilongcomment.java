package com.example.demo.elong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class yilongcomment {
    /*
    * 循环获取本地文件夹内所有文件
    * 对所有文件进行读取并转成String格式
    * 调用抓取评论方法 转成reviewJSONARRY
    * 输出转成JSONObject
    * */
    //对文件名称截取  hotelid ，评论当前页
    public static void main(String[] args) {
        List<String> list = getFileList("D:\\baifendian\\yilongpinglun");

        for(String lists:list){
            //获取hotelid
            String hotelid = getStrByPrePost(lists,"D:\\baifendian\\yilongpinglun\\","_comm");
            System.out.println("hotelid:"+hotelid);
            String url = "http://hotel.elong.com/"+hotelid+"/";
            System.out.println(url);
            //获取评论当前页页码
            String htmlss = getHtml2(url);
            String page = getStrByPrePost(lists,"_comment_",".json");
            System.out.println("第   "+page+"   页");

            Document doc = Jsoup.parse(htmlss);
            //评论的数量（下面评论的头部）	REV_INTRO	全部(9999+) 推荐(9999+)
            String pnums = doc.select(".nav_lst").text();
            System.out.println(pnums);
            //reviewJson.put("REV_INTRO", pnums);

            //评论标签以及数量	REV_TAGS	接送机方便74服务周到642
            String pbnums = doc.select(".lst_txt").text().replace("不限 ", "");
            //System.out.println(pbnums);
            JSONArray reviewJSONARRY = new JSONArray();
            JSONObject comtJson = new JSONObject();//创建评论对象
            //System.out.println("路径名字"+lists);
            String comment = readJsonData(lists);
            /*System.out.println(comment);
            System.out.println(url);
            System.out.println(page);
            System.out.println(pnums);
            System.out.println(pbnums);*/



            try{
                 reviewJSONARRY.addAll(getCommentJSON(comment, url ,page, pnums,pbnums));
             }catch(Exception e){
                 e.printStackTrace();
            }
            comtJson.put("reviewArry",reviewJSONARRY);

            contentToTxt("d://yilong/comment20190108.json",
                    comtJson.toJSONString());// 保存.json
        }
    }
    //读取当前的一个文件
    public static String readJsonData(String pactFile) /*throws IOException */{
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile); //"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("没找到文件" + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
    }

    //遍历本地文件获取文件名
    public static List<String> getFileList(String strPath) {
        List<String> filelist = new  ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        String filelistt = null;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("json")) { // 判断文件名是否以.json结尾
                    String strFileName = files[i].getAbsolutePath();
                    //System.out.println(strFileName);
                    filelistt = String.valueOf(files[i]);
                    filelist.add(filelistt);
                    //readJsonData(filelistt);
                } else {
                    continue;
                }
            }
        }
        return filelist;
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
    //评论抓取
    public static JSONArray getCommentJSON(String comments, String url, String page, String pnums, String pbnums)
            throws ParseException {
        JSONObject jsonObject = JSON.parseObject(comments);
        String value = jsonObject.getString("value");
        JSONObject jsondata = JSON.parseObject(value);//
        JSONArray commentss = jsondata.getJSONArray("Comments");//?

        JSONArray reviewArry = new JSONArray();
        Date a = new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-01");
        Date b = new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-31");

        for(int i=0;i<commentss.size();i++){
            JSONObject comment=commentss.getJSONObject(i);
            String datePublished = comment.getString("CreateTime");
            Date c = new SimpleDateFormat("yyyy-MM-dd").parse(datePublished);
            if (c.before(b) && c.after(a)) {
                JSONObject reviewObj = new JSONObject();
                //13评论的数量（下面评论的头部）	REV_INTRO	全部(9999+) 推荐(9999+)
                reviewObj.put("REV_INTRO",pnums);
                //14评论标签以及数量	REV_TAGS	接送机方便74服务周到642
                reviewObj.put("REV_TAGS",pbnums);
                //15关联url
                reviewObj.put("OBJ_URL",url);
                System.out.println("关联="+i);
                //16评论页数
                reviewObj.put("REV_PAGE",page);
                //1评论内容Content
                reviewObj.put("REV_CONTENT",comment.getString("Content"));
                //2CreateTime评论时间 REV_TIME
                reviewObj.put("REV_TIME",comment.getString("CreateTime"));
                //3IsMarrow是否精华  REV_ISQUALITY
                reviewObj.put("REV_ISQUALITY",comment.getString("IsMarrow"));
                //4酒店打分数	REV_SCORE	5.0     格式化？？？
                reviewObj.put("REV_SCORE",comment.getJSONObject("CommentScore").getInteger("Score"));
                //5评论人昵称	U_NICKNAME
                reviewObj.put("U_NICKNAME",comment.getJSONObject("CommentUser").getString("NickName"));
                //6消费级别	U_LEVEL	新手/达人    汉化？？？
                //汉化等级
                int rank = comment.getJSONObject("CommentUser").getInteger("Rank");
                String ranks = "";
                if(rank==1){
                    ranks = "新手";
                }
                else if(rank==2){
                    ranks = "专家";
                }
                else if(rank==3){
                    ranks = "达人";
                }else{
                    ranks = "未知等级";
                }
                reviewObj.put("U_LEVEL",ranks);
                //7来源	U_CHANNEL	来自手机app
                //汉化来源
                String app = null;
                int apps = comment.getInteger("Source");
                if(apps==1){
                    app = "来自手机app";
                }else{
                    app = "";
                }
                reviewObj.put("U_CHANNEL",app);
                //8评论者旅行类型	U_TRTYPE	商务出差/情侣出游
                //汉化旅行类型
                String type = "";
                int types = comment.getJSONObject("CommentExt").getInteger("TravelType");
                if (types==0){
                    type = "其他类型";
                }else if (types==1){
                    type = "家庭亲子";
                }else if (types==2){
                    type = "情侣出行";
                }else if (types==3){
                    type = "朋友出行";
                }else if (types==4){
                    type = "商务出差";
                }else if (types==5){
                    type = "独自旅行";
                }else{
                    type = "未知类型";
                }
                reviewObj.put("U_TRTYPE",type);
                //9住的房型	REV_RM_TIT	豪华大床房
                reviewObj.put("REV_RM_TIT",
                        comment.getJSONObject("CommentExt").getJSONObject("Order")
                                .getString("RoomTypeName"));
                //10图片链接集合	REV_PIC_URL
                reviewObj.put("REV_PIC_URL","");
                JSONArray pics = comment.getJSONArray("Images");
                JSONArray picurlss = new JSONArray();
                for(int j=0;j<pics.size();j++){
                    JSONObject picc = new JSONObject();
                    JSONObject picss = pics.getJSONObject(j);
                    JSONArray picurls = picss.getJSONArray("Path");
                    String imgstr=picurls.getJSONObject(0).getString("url");
                    picc.put("REV_PIC_URL",imgstr);
                    //picurlss.add(imgstr);
                    picurlss.add(picc);
                    reviewObj.put("REV_PIC_URL",picurlss);
                    //11评论照片数量	REV_PIC_NUM
                    reviewObj.put("REV_PIC_NUM", "");
                    int iii = pics.size();
                    System.out.println("iii"+iii);
                    reviewObj.put("REV_PIC_NUM", iii);
                }
                //12酒店回复
                reviewObj.put("REV_REPLY","");
                JSONArray replys = new JSONArray();
                JSONArray replyss = comment.getJSONArray("Replys");
                System.out.println("回复"+replyss.size());
                for(int p=0;p<replyss.size();p++){
                    JSONObject rep = new JSONObject();
                    JSONObject reply = replyss.getJSONObject(p);
                    String replyy = reply.getString("Content");
                    rep.put("REV_REPLY",replyy);
                    //replys.add(replyy);
                    replys.add(rep);
                    reviewObj.put("REV_REPLY",replys);
                }
                reviewArry.add(reviewObj);
            }
        }
        return reviewArry;
    }
    //静态请求
    public static String getHtml2(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");//UA[(int) (Math.random()*UA.length)]
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        request.setHeader("Cookie", "CookieGuid=db0fb4f6-aa8a-434a-bad0-5a93e05c6357; _fid=db0fb4f6-aa8a-434a-bad0-5a93e05c6357; SHBrowseHotel=cn=20201287%2C%2C%2C%2C%2C%2C%3B10101323%2C%2C%2C%2C%2C%2C%3B90668567%2C%2C%2C%2C%2C%2C%3B42003330%2C%2C%2C%2C%2C%2C%3B&; SessionGuid=253658e6-b160-439c-b53f-96029fbc9822; Esid=62b6d918-b0d7-48de-ad7f-5fdc888cc71d; com.eLong.CommonService.OrderFromCookieInfo=Orderfromtype=1&Parentid=50000&Status=1&Cookiesdays=0&Coefficient=0.0&Pkid=50&Priority=8000&Isusefparam=0&Makecomefrom=0&Savecookies=0; fv=pcweb; anti_token=76DABD0A-A753-4216-A197-51A576E1DB62; ShHotel=InDate=2019-01-07&OutDate=2019-01-08; ext_param=bns%3D4%26ct%3D3; s_cc=true; s_visit=1; s_sq=elongcom%3D%2526pid%253Dhotel.elong.com%25252F20201287%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fimall.elong.com%25252F%2526ot%253DA; JSESSIONID=FB58744867B2B9FEE92BD16A5B5710A8; route=9c6611677939d381e0674606c0949b4e");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }
    //截取
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
