package com.example.demo.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSV {


    public static void main(String[] args) {
        File file = new File("D:\\BFD\\pro\\WB\\weibo3.txt");
        List<String> list = txt2String(file);
        for(String str :list){
            System.out.println(str);
            JSONObject json = JSONObject.parseObject(str);
            String pagetype = json.getString("pagetype");
            String cate = json.getString("cate");
            String follows = json.getString("follows");
            String post = json.getString("posts");
            String brand = json.getString("brand");
            String url = json.getString("url");
            String fans = json.getString("fans");
            String username = json.getString("username");
            String cid = json.getString("cid");
            String creation_time = json.getString("creation_time");


            JSONArray arr = json.getJSONArray("weibos");

            for (int i=0;i<arr.size();i++){
                JSONObject json2 = arr.getJSONObject(i);

                String time = json2.getString("time");
                String content = json2.getString("content");
                String source = json2.getString("source");
                String commentUrl = json2.getString("commentUrl");
                String wid = json2.getString("wid");
                String weiboURL = json2.getString("weiboURL");
                String repostUrl = json2.getString("repostUrl");
                String comment = json2.getString("comment");
                String support = json2.getString("support");
                String repost = json2.getString("repost");
                JSONArray weiboImgs = json2.getJSONArray("weiboImgs");
                String k = "";
                StringBuffer kk = new StringBuffer();
                for(int j = 0;j<weiboImgs.size();j++){
                    k = weiboImgs.getString(j);
                    kk.append(k).append("|");
                 }
                String m = "";
                StringBuffer mm = new StringBuffer();
                JSONArray hashTagObjs = json2.getJSONArray("hashTagObjs");
                for(int h = 0;h<hashTagObjs.size();h++){
                    m = hashTagObjs.getString(h);
                    mm.append(m).append("|");
                }

                String n = "";
                StringBuffer nn = new StringBuffer();

                JSONArray weiboVideos = json2.getJSONArray("weiboVideos");
                for(int h = 0;h<weiboVideos.size();h++){
                    n = weiboVideos.getString(h);
                    nn.append(n).append("|");
                }
                System.out.println(nn);
                String p = "";
                StringBuffer pp = new StringBuffer();
                String q = "";
                StringBuffer qq = new StringBuffer();
                String r = "";
                StringBuffer rr = new StringBuffer();
                String forwardAuthor="";
                String content2 ="";
                JSONObject json3 = json2.getJSONObject("forward");
 //////////////////////////////////////////////////////////////////////////////////////////
                if(!json3.isEmpty()){
                    forwardAuthor = json3.getString("forwardAuthor");
                    content2 = json3.getString("content");

                    JSONArray forwardImgs = json3.getJSONArray("forwardImgs");
                    System.out.println();
                    for(int h = 0;h<forwardImgs.size();h++){
                        p = forwardImgs.getString(h);
                        pp.append(p).append("|");
                    }

                    JSONArray forwardVideos = json3.getJSONArray("forwardVideos");
                    for(int h = 0;h<forwardVideos.size();h++){
                        q = forwardVideos.getString(h);
                        qq.append(q).append("|");
                    }

                    JSONArray forwardTagObjs = json3.getJSONArray("forwardTagObjs");
                    for(int h = 0;h<forwardTagObjs.size();h++){
                        r = forwardTagObjs.getString(h);
                        rr.append(r).append("|");
                    }
                    System.out.println("!!!"+rr);


                }



                            //姓名、内容、时间、来源、评论链接、微博id、页面类型、cate、点赞数
                //
                contentToTxt("D:\\BFD\\pro\\WB\\weibo.csv",username+","+content+","+time+","
                                +source+","+url+","+commentUrl+","+wid+","+pagetype+","+cate+","+
                                follows+","+post+","+weiboURL+","+repostUrl+","+comment+","+support+
                                ","+brand+","+fans+","+cid+","+creation_time+","+repost+","+
                                kk+","+mm+","+nn+","+pp+","+qq+","+rr+","+forwardAuthor+
                                ","+content2);

             }

        }
    }






    /*
     * 按行读取数据
     * */
    public static List<String> txt2String(File file) {
        List<String> list = new ArrayList<String>();
        // StringBuilder result = new StringBuilder();
        try {
            //BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件


            InputStreamReader br = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader read = new BufferedReader(br);
            String s = null;
            while ((s = read.readLine()) != null) {
                list.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /*
     * 写入文档
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
}
