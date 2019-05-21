package com.example.demo.elong;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YiLong {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    public static void main(String[] args) /*throws IOException,ParseException*/ {
        //创建文件对象
        File file = new File("D:\\GFY\\yilong2019_4_2.txt");
        int numm = 0;
        List<String> list = txt2String(file);
        //遍历文件中所有的路径
        for (String item : list) {
            numm++;
            //String[] splited = item.split("\\s+");
            String url = item;
            System.out.println(url);
            long st = new Date().getTime();
            System.out.println(st);
            //String url = "http://hotel.elong.com/90610158/";
            String htmlss = getHtml(url);
            //调用parse()解析html格式
            Document doc = Jsoup.parse(htmlss);
            JSONObject reviewJson = new JSONObject();
            /*
            * 开始获取酒店的基本信息
            * */
            //1、酒店序号	OBJ_NUM 按抓取顺序生成
            reviewJson.put("OBJ_NUM", numm);
            //2、酒店链接	OBJ_URL
            reviewJson.put("OBJ_URL", url);
            //3、酒店路径	OBJ_PATH
            String path1 = doc.select(".link555 a").get(2).text();
            String path2 = doc.select(".link555 h1.breadcrumb").text();
            reviewJson.put("OBJ_PATH", path1 + ">" + path2);
            //4、酒店名称
            String title = doc.select(".hdetail_main h1").text();
            reviewJson.put("OBJ_TIT", title);
            //5、评论得分
            String score = doc.select(".comt_nmb").text();
            score += "分";
            reviewJson.put("OBJ_TOTSCORE", score);
            //6、酒店等级文字描述
            reviewJson.put("OBJ_LABEL", "");
            String rank = doc.select(".t24 i").attr("title");
            reviewJson.put("OBJ_LABEL", rank);
            //int numss = 0;
            Elements dl = doc.select(".dview_info dl");
            //numss = dl.size();
            ////////////////////////////////////////
            reviewJson.put("OBJ_TEL", "");
            reviewJson.put("OBJ_INTRO", "");
            reviewJson.put("OBJ_FACIINFO", "");
            reviewJson.put("OBJ_CHECKTIME", "");
            reviewJson.put("OBJ_ENT", "");
            reviewJson.put("OBJ_OPENDATE", "");
            reviewJson.put("OBJ_RESERVENOTES", "");

            for (Element dls : dl) {
                String key = dls.select("dt").text();
                if (key.equals("酒店电话")) {
                    //7、酒店电话	OBJ_TEL
                    String tel = dls.select("dd").text();
                    //String tel1 = tel.text();
                    reviewJson.put("OBJ_TEL", tel);
                }
                if (key.equals("酒店简介")) {
                    //8、酒店简介	OBJ_INTRO
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_INTRO", tel);
                }
                if (key.equals("酒店设施")) {
                    //9、酒店设施	OBJ_FACIINFO
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_FACIINFO", tel);
                }
                if (key.equals("入离时间")) {
                    //10入离时间	 OBJ_CHECKTIME
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_CHECKTIME", tel);
                }
                if (key.equals("酒店服务")) {
                    //11酒店服务	OBJ_ENT
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_ENT", tel);
                }
                if (key.equals("开业时间")) {
                    //12开业时间	OBJ_OPENDATE
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_OPENDATE", tel);
                }
                if (key.equals("预订须知")) {
                    //13预定须知	OBJ_RESERVENOTES
                    String tel = dls.select("dd").text();
                    reviewJson.put("OBJ_RESERVENOTES", tel);
                }
            }
            //14、酒店名字旁的星星的字体描述	OBJ_LEVEL
            reviewJson.put("OBJ_LEVEL", "");
            String xingxing = doc.select(".t24 b").attr("title");
            reviewJson.put("OBJ_LEVEL", xingxing);
            //15、点评下面的板块评分	OBJ_SUBSCORE  例；设施4.7服务4.7卫生4.7
            reviewJson.put("OBJ_SUBSCORE", "");
            String pingfen = doc.select(".lst_nmb").text();
            reviewJson.put("OBJ_SUBSCORE", pingfen);
            //16、可接受的信用卡 OBJ_CREIDCARD cardxxx
            reviewJson.put("OBJ_CREIDCARD", "");
            Elements cards = doc.select(".dview_info_card dd i");
            List<String> cardss = new ArrayList<String>();
            for (Element card : cards) {
                String cardsss = card.attr("class").replace("icon_", "");
                System.out.println(cardsss);
                //信用卡汉化
                if (cardsss.equals("card1")) {
                    cardsss = "美国运通卡";
                } else if (cardsss.equals("card2")) {
                    cardsss = "维萨卡";
                } else if (cardsss.equals("card3")) {
                    cardsss = "万事达卡";
                } else if (cardsss.equals("card4")) {
                    cardsss = "吉士美卡";
                } else if (cardsss.equals("card5")) {
                    cardsss = "大来卡";
                } else {
                    cardsss = "未识别的信用卡";
                }
                cardss.add(cardsss);
                reviewJson.put("OBJ_CREIDCARD", cardss);
            }
            //包含不包含的服务
            reviewJson.put("OBJ_EXSERVICE", "");
            reviewJson.put("OBJ_INSERVICE", "");
            Elements fuwus = doc.select(".dview_icon_list  li");
            //List<String> fuwusclude = new ArrayList<String>();
            List<String> unclude1s = new ArrayList<String>();
            List<String> include1s = new ArrayList<String>();
            for (Element fuwu : fuwus) {
                String unclude = fuwu.select("i").attr("class")
                        .replace("icon_view_", "");
                //fuwusclude.add(unclude);
                //reviewJson.put("OBJ_TEST", fuwusclude);
                String str = unclude;
                String regex = "\\d*\\ ['n']['o']";
                boolean flag = str.matches(regex);
                System.out.println(flag);
                Elements fuwus1 = fuwu.select("p");
                if (flag) {
                    //17、酒店信息下不包含的服务（浅色）	OBJ_EXSERVICE
                    for (Element fuwus2 : fuwus1) {
                        String str1 = fuwus2.text();
                        unclude1s.add(str1);
                        reviewJson.put("OBJ_EXSERVICE", unclude1s);
                    }
                } else {
                    //18、酒店信息下包含的服务（深色）	OBJ_INSERVICE
                    for (Element fuwus2 : fuwus1) {
                        String str1 = fuwus2.text();
                        include1s.add(str1);
                        reviewJson.put("OBJ_INSERVICE", include1s);
                    }
                }
            }
            /*
            * 酒店基本信息结束
            * */

            //获取评论的总数量           //有点小问题
            String numsss = doc.select(".hrela_comt_total").text();
            Pattern pattern = Pattern.compile("\\d+");
            //Pattern p = Pattern.compile(renumss);
            Matcher matcher = pattern.matcher(numsss);
            if (matcher.find()) {
                System.out.println(matcher.group());
            }
            Integer i = Integer.valueOf(matcher.group());
            int comtPageNum = i / 20;
            System.out.println("共" + comtPageNum + "页");
            //int comtPageNum = 1; 测试备用

            //评论的数量（下面评论的头部）	REV_INTRO	全部(9999+) 推荐(9999+)
            String pnums = doc.select(".nav_lst").text();
            //reviewJson.put("REV_INTRO", pnums);
            //评论标签以及数量	REV_TAGS	接送机方便74服务周到642
            String pbnums = doc.select(".lst_txt").text().replace("不限 ", "");
            //reviewJson.put("REV_TAGS", pbnums);

            //System.out.println(pnums);

            //存储静态页面文本
            contentToTxt("D:\\GFY\\yilongtext.txt",
                    reviewJson.toJSONString());// 保存txt

            // url相关参数 http://hotel.elong.com/10101323/
            String hotelId = getStrByPrePost(url, "http://hotel.elong.com/", "/");
            System.out.println("140行" + hotelId);
            String recommendedType = "0";
            String mainTagId = "0";
            String subTagId = "0";
            String rankType = "0";
            String eToken = "db0fb4f6-aa8a-434a-bad0-5a93e05c6357";
            String code = "7243459";

            Map<String, String> param = new HashMap<String, String>();
            //param.put("comtPageNum", comtPageNum);
            param.put("hotelId", hotelId);
            param.put("recommendedType", recommendedType);
            param.put("mainTagId", mainTagId);
            param.put("subTagId", subTagId);
            param.put("rankType", rankType);
            param.put("eToken", eToken);
            param.put("code", code);


        /*//获取秘钥
        preGetCode("http://hotel.elong.com/ajax/detail/getcode.html?detailRequest.bookingChannel=1&detailRequest.cardNo=192928&detailRequest.checkInDate=2019-01-10&detailRequest.checkOutDate=2019-01-11&detailRequest.customerLevel=11&detailRequest.hotelIDs=90610158&detailRequest.isAfterCouponPrice=true&detailRequest.isDebug=false&detailRequest.isLogin=false&detailRequest.isMobileOnly=false&detailRequest.isNeed5Discount=false&detailRequest.isTrace=false&detailRequest.language=cn&detailRequest.needDataFromCache=true&detailRequest.orderFromID=50&detailRequest.payMethod=0&detailRequest.productType=0&detailRequest.promotionChannelCode=0000&detailRequest.proxyID=ZD&detailRequest.sellChannel=1&detailRequest.settlementType=0&detailRequest.updateOrder=false&detailRequest.elongToken=db0fb4f6-aa8a-434a-bad0-5a93e05c6357"
                ,"http://hotel.elong.com/11401105/");
            //调用房型第一次的get请求
            String date1 = "2019-01-11";
            String date2 = "2019-01-12";
            String key = getKey(st, date1, date2, hotelId);
            //解密得到key
            //测试备用 String key = "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";
        //通过解密文件返回参数code （当前读取js文件有问题，此方法暂时不能用）
        try {
            String codeStr=getcode(key,date1,date2,hotelId);
            System.out.println("qpp+==="+codeStr);
        }catch (Exception e) {
            e.printStackTrace();
        }*/

        //System.out.println("key"+key);/////////////////////////////////////////////////////////

        /*//创建评论对象
        JSONObject jsonObject = returnXCJson(url, param, pnums, pbnums, comtPageNum);//////////////////

            //存储评论文本
            contentToTxt("d://yilong/comment20190108.json",
            jsonObject.toJSONString());// 保存.json

            JSONObject fangxingJson = new JSONObject();//创建房型对象
            JSONArray fangxingJSONARRY = new JSONArray();
            try {
                String fangxing1 = AsynCommentView(hotelId);
                fangxingJSONARRY.addAll(getfangxingJSON(fangxing1, url));
                fangxingJson.put("reviewArry", fangxingJSONARRY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //存储房型文本
            contentToTxt("d://yilong/fangxing20190108.json",
                    fangxingJson.toJSONString());// 保存.json*/

        }
        numm++;
    }
    //静态请求
    public static String getHtml(String url) {
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


    /*
    * 自动读取本地json文件
    *      转为JSONArray
    * */
    /* StringBuilder res = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            while(br.ready()){
                //这里可以作相关的处理过程 #todo your code#
                res.append(br.readLine() + "\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }
  */
    /*//遍历本地文件
    public static JSONArray GetAsynCommentView2(String strPath) {
        List<String> filelistt = getFileList(strPath);
        JSONArray comments = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (String filelist : filelistt) {
            jsonObject = JSON.parseObject(filelist);
            String value = jsonObject.getString("value");
            JSONObject jsondata = JSON.parseObject(value);//
            comments = jsondata.getJSONArray("Comments");//?
            String url=null;
            String pnums=null;
            String pbnums=null;
            int page = 0;
            try{
                getCommentJSON(comments,url,page,pnums,pbnums);
            }catch (Exception e){

            }

        }
        return comments;
    }
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
                    System.out.println("---" + strFileName);
                    filelistt = String.valueOf(files[i]);
                    filelist.add(filelistt);
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }*/

    /*public static void main(String[] args) {
		String path = "D:\\JAVA";		//要遍历的路径
		File file = new File(path);		//获取其file对象
		File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
		for(File f:fs){					//遍历File[]数组
			if(!f.isDirectory())		//若非目录(即文件)，则打印
				System.out.println(f);
		}
	}*/

    /*//get请求url变化
    public static JSONObject returnXCJson(String url, Map<String, String> param,String pnums,String pbnums,int inum) {
            JSONObject comtJson = new JSONObject();//创建评论对象
            JSONArray reviewJSONARRY = new JSONArray();
             //评论部分start
             int ii = 0;
                while (true) {
                    if (ii == 1) {
                        break;
                    }
            //请求头
            // String headPath = StringUtils.substringBefore(url, "?");//url '?' 向前截取;
            String referer = "http://hotel.elong.com/"+param.get("hotelId")+"/";
            //String ajaxPath = "http://hotel.elong.com/ajax/comment/getcommentbypage/?hotelId=20201287&recommendedType=0&pageIndex=0&mainTagId=0&subTagId=0&rankType=0&eToken=db0fb4f6-aa8a-434a-bad0-5a93e05c6357&code=7614146";
            String ajaxPath = "http://hotel.elong.com/ajax/comment/getcommentbypage/?hotelId="
                    + param.get("hotelId")
                    + "&recommendedType="
                    + param.get("recommendedType")
                    + "&pageIndex="
                    + ii
                    + "&mainTagId="
                    + param.get("mainTagId")
                    + "&subTagId="
                    + param.get("subTagId")
                    + "&rankType="
                    + param.get("rankType")
                    + "&eToken="
                    + param.get("eToken")
                    + "&code="
                    + param.get("code");
                    System.out.println("ajaxPath"+ajaxPath);
            JSONArray pldoc =  GetAsynCommentView(ajaxPath, referer);

            if (pldoc == null) {
                break;
            }
            try{
                reviewJSONARRY.addAll(getCommentJSON(pldoc, url ,ii, pnums,pbnums));
            }catch (Exception e1){
                e1.printStackTrace();
            }
            ii++;
        }
        comtJson.put("reviewArry", reviewJSONARRY);
        // 评论部分end
        return comtJson;
    }*/
    //get请求
    public static JSONArray GetAsynCommentView(String url, String referer) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept",
                "application/json, text/javascript, */*; q=0.01");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "_fid=db0fb4f6-aa8a-434a-bad0-5a93e05c6357; newjava1=62659afd2ede73f6fcd5c09d53222f10; CookieGuid=ec11bd69-d33b-4e72-9c6c-a7533ab6b491; SessionGuid=042b0bf8-86d1-4abb-b214-84083e57f588; Esid=a6e61965-c8cc-435b-80aa-d4dc763fb6b5; com.eLong.CommonService.OrderFromCookieInfo=Orderfromtype=1&Parentid=50000&Status=1&Cookiesdays=0&Coefficient=0.0&Pkid=50&Priority=8000&Isusefparam=0&Makecomefrom=0&Savecookies=0; fv=pcweb; anti_token=9EE7A988-DD2B-44E3-8E55-833B3B0F728E; ShHotel=InDate=2019-01-07&OutDate=2019-01-08; ext_param=bns%3D4%26ct%3D3; s_cc=true; __tctmd=0.1; SHBrowseHotel=cn=20201287%2C%2C%2C%2C%2C%2C%3B&; JSESSIONID=16CD3F6B628870E637D8C4013E0E62A2; s_visit=1; s_sq=%5B%5BB%5D%5D; __tctmc=0.183335743");
        request.setHeader("Host", "hotel.elong.com");
        request.setHeader("Referer", referer);
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);
        JSONObject jsonObject = JSON.parseObject(htmlContent);
        String value = jsonObject.getString("value");
        JSONObject jsondata = JSON.parseObject(value);//
        JSONArray comments = jsondata.getJSONArray("Comments");//?
      return comments;
    }

    //评论抓取
    public static JSONArray getCommentJSON(JSONArray comments, String url, int page, String pnums, String pbnums)
            throws ParseException {
        JSONArray reviewArry = new JSONArray();
        Date a = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-31");
        Date b = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-10");

        for(int i=0;i<comments.size();i++){
             JSONObject comment=comments.getJSONObject(i);
            String datePublished = comment.getString("CreateTime");
            Date c = new SimpleDateFormat("yyyy-MM-dd").parse(datePublished);
             if (c.before(b) && c.after(a)) {
                 JSONObject reviewObj = new JSONObject();
                //13评论的数量（下面评论的头部）	REV_INTRO	全部(9999+) 推荐(9999+)
                reviewObj.put("REV_INTRO",pnums);
                //14评论标签以及数量	REV_TAGS	接送机方便74服务周到642
                reviewObj.put("REV_TAGS",pbnums);
                //15关联url
                reviewObj.put("REV_URL2",url);
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
                String app = "未知来源";
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
                JSONArray pics = comment.getJSONArray("Images");
                JSONArray picurlss = new JSONArray();
                for(int j=0;j<pics.size();j++){
                    JSONObject picc = new JSONObject();
                    JSONObject picss = pics.getJSONObject(j);
                    JSONArray picurls = picss.getJSONArray("Path");
                    String imgstr=picurls.getJSONObject(0).getString("url");
                    picc.put("REV_REPLY",imgstr);
                    //picurlss.add(imgstr);
                    picurlss.add(picc);
                    reviewObj.put("REV_REPLY",picurlss);
                     //11评论照片数量	REV_PIC_NUM
                    int iii = pics.size();
                    System.out.println("iii"+iii);
                    reviewObj.put("REV_PIC_NUM", iii);
                 }
                    //12酒店回复
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

    //post请求
    public static String AsynCommentView(String hotelIDs) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //请求url
        HttpPost post =new HttpPost("http://hotel.elong.com/ajax/tmapidetail/gethotelroomsetjvajson");

        List<NameValuePair> param = new ArrayList<>();

        param.add(new BasicNameValuePair("bookingChannel","1"));
        param.add(new BasicNameValuePair("cardNo","192928"));
        param.add(new BasicNameValuePair("checkInDate","2019-01-09"));
        param.add(new BasicNameValuePair("checkOutDate","2019-01-10"));

        param.add(new BasicNameValuePair("customerLevel","11"));
        param.add(new BasicNameValuePair("hotelIDs",hotelIDs));///////////////////////////////////
        System.out.println(hotelIDs);
        param.add(new BasicNameValuePair("isAfterCouponPrice","true"));
        param.add(new BasicNameValuePair("isDebug","false"));
        param.add(new BasicNameValuePair("isLogin","false"));

        param.add(new BasicNameValuePair("isMobileOnly","false"));
        param.add(new BasicNameValuePair("isNeed5Discount","false"));
        param.add(new BasicNameValuePair("isTrace","false"));
        param.add(new BasicNameValuePair("language","cn"));
        param.add(new BasicNameValuePair("needDataFromCache","true"));

        param.add(new BasicNameValuePair("orderFromID","50"));
        param.add(new BasicNameValuePair("payMethod","0"));
        param.add(new BasicNameValuePair("productType","0"));
        param.add(new BasicNameValuePair("promotionChannelCode","0000"));
        param.add(new BasicNameValuePair("proxyID","ZD"));

        param.add(new BasicNameValuePair("sellChannel","1"));
        param.add(new BasicNameValuePair("settlementType","0"));
        param.add(new BasicNameValuePair("updateOrder","false"));
        param.add(new BasicNameValuePair("elongToken","db0fb4f6-aa8a-434a-bad0-5a93e05c6357"));
        param.add(new BasicNameValuePair("code","8933945"));///////////////////////////

        post.setHeader("Accept","*/*");
        post.setHeader("Accept-Encoding","gzip, deflate");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Connection","keep-alive");

        post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Cookie","_fid=db0fb4f6-aa8a-434a-bad0-5a93e05c6357; newjava1=62659afd2ede73f6fcd5c09d53222f10; CookieGuid=ec11bd69-d33b-4e72-9c6c-a7533ab6b491; SessionGuid=042b0bf8-86d1-4abb-b214-84083e57f588; Esid=a6e61965-c8cc-435b-80aa-d4dc763fb6b5; com.eLong.CommonService.OrderFromCookieInfo=Orderfromtype=1&Parentid=50000&Status=1&Cookiesdays=0&Coefficient=0.0&Pkid=50&Priority=8000&Isusefparam=0&Makecomefrom=0&Savecookies=0; fv=pcweb; anti_token=9EE7A988-DD2B-44E3-8E55-833B3B0F728E; ext_param=bns%3D4%26ct%3D3; s_cc=true; __tctmd=0.1; SHBrowseHotel=cn=20201287%2C%2C%2C%2C%2C%2C%3B10101323%2C%2C%2C%2C%2C%2C%3B&; ShHotel=InDate=2019-01-09&OutDate=2019-01-10; s_visit=1; __tctmb=0.475882273057871.1546997758122.1546997758122.1; JSESSIONID=B55E88A85085E5CD76C375B628E6D9A9; s_sq=%5B%5BB%5D%5D; __tccgd=0.0; __tctmc=0.204119817");
        post.setHeader("Origin","https://you.ctrip.com");
        post.setHeader("Host","hotel.elong.com");
        post.setHeader("Origin","http://hotel.elong.com");
        post.setHeader("Referer","http://hotel.elong.com/"+hotelIDs+"/");
         //post.setHeader("Referer","http://hotel.elong.com/20201287/");

        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        post.setHeader("X-Requested-With","XMLHttpRequest");

        post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
        CloseableHttpResponse response =httpClient.execute(post);
        //int statusCode =response.getStatusLine().getStatusCode();
        HttpEntity entity =response.getEntity();
        String posthtml = EntityUtils.toString(entity,"utf-8");
        response.close();
        httpClient.close();
        System.out.println("post请求"+posthtml);

        return posthtml;
    }
    /*
    * 房型抓取
    *
    * */
    public static JSONArray getfangxingJSON(String posthtml, String url) {
        JSONObject jsonObject = JSON.parseObject(posthtml);
        String hotelInventory = jsonObject.getString("hotelInventory");//
        JSONObject hotelInventorys = JSON.parseObject(hotelInventory);//
        JSONArray rooms = hotelInventorys.getJSONArray("rooms");//?

        JSONArray roomsArry = new JSONArray();
        System.out.println("349行"+rooms.size());
        for(int i=0;i<rooms.size();i++){
            JSONObject roomss=rooms.getJSONObject(i);
            JSONObject roomsObj = new JSONObject();
            //关联url
            roomsObj.put("REV_URL3",url);
            System.out.println("关联="+i);

            //房间名称	RM_TIT	豪华双床间
            roomsObj.put("RM_TIT",roomss.getString("roomTypeName"));

            //房间总描述	RM_INTRO	房间38㎡ 大/双床可住：2 等
            roomsObj.put("RM_INTRO",
                    "房间："+roomss.getString("area")+"m^2"+
                            "|"+roomss.getString("bedTypeName")+
                            "|可住："+roomss.getString("maxPersonOfRoom")+
                            "|楼层："+roomss.getString("floor")+
                            "|"+roomss.getString("allRoomFreeNetworkDesc"));

            //房间最低价格	RM_BASEPRICE
            JSONArray products = roomss.getJSONArray("products");
            String picurl = products.getJSONObject(0).getString("avgPrice");
            roomsObj.put("RM_BASEPRICE",picurl);

            //产品数量	RM_SKU_NUM	共9个产品
            roomsObj.put("RM_SKU_NUM", products.size());
            JSONArray yuding = new JSONArray();
            String zhifus = null;
            for(int j=0;j<products.size();j++){
                JSONObject yudings = new JSONObject();
                JSONObject yudingsss = products.getJSONObject(j);
                String cancelRuleDesc = yudingsss.getString("cancelRuleDesc");
                JSONArray zhifu = yudingsss.getJSONArray("productAttachDesc");
                for(int k=0;k<zhifu.size();k++){
                    JSONObject zhifuss = zhifu.getJSONObject(k);
                    String key = zhifuss.getString("key");
                    if(key.equals("支付方式")){
                            zhifus = zhifuss.getString("value");
                    }
                }
                //支付方式	RM_PAYINFO	预定/在线付
                yudings.put("RM_PAYINFO",zhifus);
                //预定规则	RM_BOOKRULE	不可取消
                yudings.put("RM_BOOKRULE",cancelRuleDesc);
                //////////////////
                /*JSONObject yudinggs = new JSONObject();
                if(yudings.equals("订单一经确认成功，不可取消或变更；如未能如约入住，您的预付房费将不予退还。")){
                    yudinggs.put("RM_BOOKRULE","不可取消");
                }else if(yudings.equals("订单提交后可随时免费取消/变更。")){
                    yudinggs.put("RM_BOOKRULE","免费取消");
                }*/
                yuding.add(yudings);
            roomsObj.put("RM_PAYINFO_PAYINFO",yuding);
            }


            //数据抓取时间
            roomsObj.put("RM_TODAY","2019-01-11");


            roomsArry.add(roomsObj);

        }
        return roomsArry;
    }

    /*
    * 写入文本
    *
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
    * 截取
    *
    * */
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
    /*//获取房型get请求的url参数
    public static String getKey(long st,String date1,String date2,String hotelIDs){
        //JSONObject jsonObject = new JSONObject();
        // String headPath = StringUtils.substringBefore(url, "?");//url '?' 向前截取;
        String referer = "http://hotel.elong.com/"+hotelIDs+"/";
        String ajaxPath = "http://hotel.elong.com/ajax/detail/getcode.html?"
                + "detailRequest.bookingChannel="
                        + "1"
                + "&detailRequest.cardNo="
                        + "192928"
                + "&detailRequest.checkInDate="
                        + date1
                + "&detailRequest.checkOutDate="//4
                        + date2
                + "&detailRequest.customerLevel="
                        + "11"
                + "&detailRequest.hotelIDs="
                        + hotelIDs
                + "&detailRequest.isAfterCouponPrice="
                        + "true"
                + "&detailRequest.isDebug="//8
                        + "false"
                + "&detailRequest.isLogin="//9
                        + "false"
                + "&detailRequest.isMobileOnly="//10
                        + "false"
                + "&detailRequest.isNeed5Discount="//11
                        + "false"
                + "&detailRequest.isTrace="//12
                        + "false"
                + "&detailRequest.language="//13
                        + "cn"
                + "&detailRequest.needDataFromCache="//14
                        + "true"
                + "&detailRequest.orderFromID="//15
                        + "50"
                + "&detailRequest.payMethod="//16
                        + "0"
                + "&detailRequest.productType"//17
                        + "0"
                + "&detailRequest.promotionChannelCode="//18
                        + "0000"
                + "&detailRequest.proxyID="//19
                        + "ZD"
                + "&detailRequest.sellChannel="//20
                        + "1"
                + "&detailRequest.settlementType="//21
                        + "0"
                + "&detailRequest.updateOrder="//22
                        + "false"
                + "&detailRequest.elongToken="//23
                        + "db0fb4f6-aa8a-434a-bad0-5a93e05c6357"
                + "&_="//24
                        + st;
        System.out.println("ajaxPath"+ajaxPath);

        String pregetcode =  preGetCode(ajaxPath, referer);


        return pregetcode;
    }*/


    //发送get请求，获取key值
    //get请求

    public static String preGetCode(String url, String referer) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept",
                "application/json, text/javascript, */*; q=0.01");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "_fid=db0fb4f6-aa8a-434a-bad0-5a93e05c6357; newjava1=62659afd2ede73f6fcd5c09d53222f10; CookieGuid=ec11bd69-d33b-4e72-9c6c-a7533ab6b491; SessionGuid=042b0bf8-86d1-4abb-b214-84083e57f588; Esid=a6e61965-c8cc-435b-80aa-d4dc763fb6b5; fv=pcweb; anti_token=9EE7A988-DD2B-44E3-8E55-833B3B0F728E; ext_param=bns%3D4%26ct%3D3; s_cc=true; __tctmd=0.1; iv=1234567890555155; com.eLong.CommonService.OrderFromCookieInfo=Pkid=50&Parentid=50000&Coefficient=0.0&Status=1&Priority=8000&Makecomefrom=0&Savecookies=0&Cookiesdays=0&Isusefparam=0&Orderfromtype=1; IHotelSearch=InDate%3D2019-01-09%26OutDate%3D2019-01-10; ShHotel=InDate=2019-01-10&OutDate=2019-01-11; newjava2=444a04864e600f7f426ccc9f154db69b; __tccgd=0.0; s_sq=%5B%5BB%5D%5D; __tctmc=0.204119817; JSESSIONID=E842983B3EFA4B4AA41D90EC9BC5A5B0; s_visit=1; SHBrowseHotel=cn=11401105%2C%2C%2C%2C%2C%2C%3B20201287%2C%2C%2C%2C%2C%2C%3B90610158%2C%2C%2C%2C%2C%2C%3B01801038%2C%2C%2C%2C%2C%2C%3B92188952%2C%2C%2C%2C%2C%2C%3B&");
        request.setHeader("Host", "hotel.elong.com");
        request.setHeader("Referer", referer);
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        System.out.println("307行" + htmlContent);

        JSONObject jsonObject = JSON.parseObject(htmlContent);
        String value = jsonObject.getString("value");
        System.out.println(value);
        return value;
    }


    //调用js方法解密key值
    /*
    * java读取js文件
    *
    * */
    private static String getcode(String key,String start_date,String end_date, String hotel_id) throws Exception{

        //engine.eval(script);
        engine.eval(new FileReader("D:\\workdoc"+"/aes.js"));//D:\workdoc\aes.js
        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("getcode",key,start_date,end_date,hotel_id);
    }
    /**/
    public static List<String> txt2String(File file) {
        List<String> list = new ArrayList<String>();
        // StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                // result.append(System.lineSeparator()+s);
                list.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}