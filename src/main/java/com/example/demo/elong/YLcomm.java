package com.example.demo.elong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.util.List;


/**
 * @author songdesheng
 * @version 1.0
 * @date 2019/4/15 9:37
 */
public class YLcomm {

    public static void main(String[] args) throws InterruptedException {

        String URL = "jdbc:mysql://172.18.1.181:3306/Guofayuan?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "crawl";
        String PASSWORD = "crawl";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("链接数据库");
            PreparedStatement pst = conn.prepareStatement("select * from yilong_comment");
            System.out.println("执行语句");

            //Integer id;
            String REV_TAGS = "";
            String REV_CONTENT = "";
            String REV_PAGE = "";
            String REV_PIC_URL = "";
            String U_CHANNEL = "";
            String U_LEVEL = "";
            String REV_ISQUALITY = "";
            String REV_INTRO = "";
            String OBJ_URL = "";
            String REV_RM_TIT = "";
            String REV_SCORE = "";
            String U_TRTYPE = "";
            String REV_TIME = "";
            String REV_REPLY = "";
            String U_NICKNAME = "";
            String hotel_id = "";
            String REV_PIC_NUM = "";
            ResultSet rs1 = pst.executeQuery();
            System.out.println(rs1.next());
            int page2 = 1;
            String hotel_id2 = "20201287";

            JSONObject comtJson = new JSONObject();
            //JSONArray reviewJSONARRY = new JSONArray();
            JSONArray reviewArry = new JSONArray();
            while (rs1.next()) {
                REV_PAGE = rs1.getString("REV_PAGE");
                hotel_id = rs1.getString("hotel_id");
                int page = Integer.valueOf(REV_PAGE);
                //取到第二页时需要存一次数据
                if(page!=page2){
                    if(!hotel_id2.equals(hotel_id)){
                        //hotel_id,变了，页码重新开始,hotel_id也重新定义
                        hotel_id2 = hotel_id;
                        page2=0;
                        System.out.println("新的id"+hotel_id2);
                    }
                    page2++;//page = 2;

                    //做一个日期的判断
                    contentToTxt("D:\\GFY\\comment20190415.json",comtJson.toJSONString());//把之前的数据写入
                    //清空对象
                    reviewArry.clear();
                    comtJson.clear();
                    //继续存值
                 }
                //前一个hotel_id只有一页
                if(!hotel_id2.equals(hotel_id)){//避免只有一页的hotel_id 跟下一个hotel_id写到一块json了
                    hotel_id2 = hotel_id;
                    contentToTxt("D:\\GFY\\comment20190415.json",comtJson.toJSONString());//把之前的数据写入
                    //清空对象
                    reviewArry.clear();
                    comtJson.clear();
                }
                System.out.println("hotel_id==="+hotel_id);

                  REV_CONTENT = rs1.getString("REV_CONTENT");
                  REV_TAGS = rs1.getString("REV_INTRO");
                  REV_PIC_URL = rs1.getString("REV_PIC_URL");
                  U_CHANNEL = rs1.getString("U_CHANNEL");
                  U_LEVEL = rs1.getString("U_LEVEL");
                  REV_ISQUALITY = rs1.getString("REV_ISQUALITY");
                  REV_INTRO = rs1.getString("REV_TAGS");
                  OBJ_URL = rs1.getString("OBJ_URL");
                  REV_RM_TIT = rs1.getString("REV_RM_TIT");
                  REV_SCORE = rs1.getString("REV_SCORE");
                  U_TRTYPE = rs1.getString("U_TRTYPE");
                  REV_TIME = rs1.getString("REV_TIME");
                  REV_REPLY = rs1.getString("REV_REPLY");
                  U_NICKNAME = rs1.getString("U_NICKNAME");
                  REV_PIC_NUM = rs1.getString("REV_PIC_NUM");


                 //创建评论对象 小块
                JSONObject reviewObj = new JSONObject();
                reviewObj.put("REV_CONTENT",REV_CONTENT);
                reviewObj.put("REV_TAGS",REV_TAGS);
                reviewObj.put("REV_PAGE",REV_PAGE);
                //截取图片
                //http://pavo.elongstatic.com/i/DP60_60/000fLBHT.jpg
                // //pavo.elongstatic.com/i/Comment750_750/FRes9F8OBi.jpg,
                // //pavo.elongstatic.com/i/Comment750_750/FWeufnlPwc.jpg,//pavo.elongstatic.com/i/Comment750_750/FWezRN7ck0.jpg,//pavo.elongstatic.com/i/Comment750_750/FWewLqbMVa.jpg,//pavo.elongstatic.com/i/Comment750_750/FWexUDwfcI.jpg,//pavo.elongstatic.com/i/Comment750_750/FWevjMlS5W.jpg,
                String[] pics =  REV_PIC_URL.split(",");
                JSONArray picurlss = new JSONArray();
                //图片的
                for(int j=0;j<pics.length;j++){
                    JSONObject picc = new JSONObject();
                    picc.put("REV_PIC_URL",pics[j]);
                    if(!pics[j].isEmpty()){
                        picurlss.add(picc);
                    }

                 }
                reviewObj.put("REV_PIC_NUM",REV_PIC_NUM);
                reviewObj.put("REV_PIC_URL",picurlss);
                reviewObj.put("U_CHANNEL",U_CHANNEL);
                reviewObj.put("U_LEVEL",U_LEVEL);
                reviewObj.put("REV_ISQUALITY",REV_ISQUALITY);
                reviewObj.put("REV_INTRO",REV_INTRO);
                reviewObj.put("OBJ_URL",OBJ_URL);
                reviewObj.put("REV_RM_TIT",REV_RM_TIT);
                reviewObj.put("REV_SCORE",REV_SCORE);
                reviewObj.put("U_TRTYPE",U_TRTYPE);
                reviewObj.put("REV_TIME",REV_TIME);
                //回复arr
                JSONArray replys = new JSONArray();
                for(int i=0;i<1;i++){
                    JSONObject replycc = new JSONObject();
                    if(!REV_REPLY.isEmpty()){
                        replycc.put("REV_REPLY",REV_REPLY);
                        replys.add(replycc);
                    }

                }
                reviewObj.put("REV_REPLY",replys);
                reviewObj.put("U_NICKNAME",U_NICKNAME);
                //根据时间判断，决定要不要放进去
                Date a = null;
                Date b = null;
                Date c = null;
                try {
                    a = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-30");
                    b = new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-01");
                    c = new SimpleDateFormat("yyyy-MM-dd").parse(REV_TIME);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    System.out.println(c);
                    if(c.before(b) && c.after(a)){
                        reviewArry.add(reviewObj);
                    }
                comtJson.put("reviewArry",reviewArry);
                System.out.println("========="+comtJson);

                }
            rs1.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
}
