package com.example.demo.ebxtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBxcarList {
    public static void main(String[] args) {
        String url = "http://www.xcar.com.cn/bbs/viewthread.php?tid=80745099";
        String html = gethtml(url);//http://zhannei.baidu.com/cse/search?q=%E4%BD%B0%E8%8D%89%E9%9B%86&p=53&s=16068099689117862280&entry=1
        System.out.println(html);
        /*String arry2 = html.substring(9,html.length()-1);
        System.out.println(arry2);
        JSONObject jsondata = JSON.parseObject(arry2);//转为obj格式
        JSONObject bbsModelList = jsondata.getJSONObject("togetherResult");
        //String num = bbsModelList.getString("totalPageNums");修改前为社区
        String num = bbsModelList.getString("totalPage");
        Integer num1 = Integer.valueOf(num);
        //JSONArray bbsList = bbsModelList.getJSONArray("bbsList");//得到list,改前为社区
        JSONArray bbsList = bbsModelList.getJSONArray("togetherViewList");
        Map<String, Object> resultData = new HashMap<>();
                System.out.println("20190212卡汽车列表有问题20190219: ==="+num1);
        List<Map<String, Object>> items = new ArrayList<>();
        for(int i=0;i<bbsList.size();i++){
            JSONObject reviewObj = new JSONObject();
            JSONObject bbs=bbsList.getJSONObject(i);
            if(bbs.containsKey("views")){
                System.out.println("dhfjdkjfk===jhghhjh"+i);
                reviewObj.put("浏览数",bbs.getString("views"));//浏览数
            }
            if(bbs.containsKey("num_xtv")){
                reviewObj.put("浏览数",bbs.getString("num_xtv"));
            }
            if(bbs.containsKey("replies")){
                reviewObj.put("回复数",bbs.getString("replies"));//回复数
            }
            if(bbs.containsKey("publish_time")){
                reviewObj.put("发表时间",bbs.getString("publish_time"));//发表时间
            }
            if(bbs.containsKey("title")){
                String regex = "<[^>]*>";
                reviewObj.put("主题",bbs.getString("title").replaceAll(regex, ""));
             }
            if(bbs.containsKey("fourm_name")){
                reviewObj.put("论坛名称",bbs.getString("fourm_name"));
            }
            if(bbs.containsKey("post_url")){
                reviewObj.put("社区-url",bbs.getString("post_url"));
            }
            if(bbs.containsKey("replyNums_info")){
                reviewObj.put("评论数",bbs.getString("replyNums_info"));
            }
            if(bbs.containsKey("praise_num_info")){
                reviewObj.put("点赞数",bbs.getString("praise_num_info"));
            }
            if(bbs.containsKey("title_info")){
                String regex = "<[^>]*>";
                reviewObj.put("主题",bbs.getString("title_info").replaceAll(regex, ""));
            }
            if(bbs.containsKey("source_info")){
                reviewObj.put("论坛名称",bbs.getString("source_info"));
            }
            if(bbs.containsKey("info_url")){
                reviewObj.put("url",bbs.getString("info_url"));
            }
            if(bbs.containsKey("source_xtv")){
                reviewObj.put("来源",bbs.getString("source_xtv"));
            }
            if(bbs.containsKey("pdate_xtv")){
                reviewObj.put("发表时间",bbs.getString("pdate_xtv"));
            }
            if(bbs.containsKey("title_xtv")){
                reviewObj.put("主题",bbs.getString("title_xtv"));
            }
            if(bbs.containsKey("xtvURL")){
                reviewObj.put("url",bbs.getString("xtvURL"));
            }
            items.add(reviewObj);
            System.out.println(reviewObj);
        }
        System.out.println(items);
        resultData.put("items",items);*/
    }


     public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
//        request.setHeader("accept-encoding", "gzip, deflate, br");
//        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
//        request.setHeader("Host", "sou.xcar.com.cn");
//        request.setHeader("Connection", "keep-alive");
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "_Xdwuv=5c48251ebef16; _PVXuv=5c48a80d413fc; bbs_cookietime=31536000; _fwck_www=9d501d52a491364cb275d7c47bac0d9e; _appuv_www=b51df14e0510e1114c26abad7560fe73; _fwck_my=7ee110d4ef495d175543aeca84981184; _appuv_my=d83f990e57cf7418d8ec51e5b102f509; ft_c_guide=1; QuickMsgQuickId=y895www.xcar.com.cn925; _Xdwnewuv=1; ad__city=63; _locationInfo_=%7Burl%3A%22http%3A%2F%2Fshenyang.xcar.com.cn%2F%22%2Ccity_id%3A%2263%22%2Cprovince_id%3A%226%22%2Ccity_name%3A%22%25E6%25B2%2588%25E9%2598%25B3%22%7D; fw_slc=1%3A1553752250%3B1%3A1553752261%3B1%3A1553752290%3B1%3A1553752292%3B1%3A1553752304; fw_pvc=1%3A1553752250%3B1%3A1553752256%3B1%3A1553752261%3B1%3A1553752304%3B1%3A1553752310; fw_exc=1%3A1553752261%3B1%3A1553752304%3B1%3A1553752310%3B1%3A1553752314%3B1%3A1553752719; fw_clc=1%3A1553752251%3B1%3A1553752261%3B1%3A1553752672%3B1%3A1553752719%3B1%3A1553752722; isRemoveCookie=1; Hm_lvt_53eb54d089f7b5dd4ae2927686b183e0=1552462144,1553752249,1553753194,1553753301; bbs_oldtopics=D80745099D30429753D80753197D80774080D; bbs_visitedfid=44D46D449D1109D118D91D47D120D878D114D1651D96D23; uv_firstv_refers=; NSC_tizu-ydbs-ex-80=ffffffff0928363345525d5f4f58455e445a4a423660; Hm_lpvt_53eb54d089f7b5dd4ae2927686b183e0=1553755100; _Xdwstime=1553755102");
        request.setHeader("upgrade-insecure-requests", "1");
        request.setHeader("referer","http://search.xcar.com.cn/metasearch.php");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("Accept", "*/*");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }
}
