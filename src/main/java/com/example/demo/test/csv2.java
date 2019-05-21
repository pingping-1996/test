package com.example.demo.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class csv2 {
    public static void main(String[] args) {
        File file = new File("D:\\BFD\\pro\\WB\\user.txt");
        List<String> list = txt2String(file);
        for(String str:list){
            Map<String,Object>  map = JSONObject.parseObject(str);

            String sex = (String) map.get("性别");
            String info = (String) map.get("简介");
            String name = (String)map.get("昵称");
            String diqu = (String)map.get("地区");
            String learn = (String)map.get("学习经历");
            String work = (String)map.get("工作经历");
            String ganqing = (String) map.get("感情状况");
            String label = (String)map.get("标签");
            String daren = (String) map.get("达人");
            String renzheng = (String)map.get("认证");
            String renzhenginfo = (String)map.get("认证信息");

            contentToTxt("D:\\BFD\\pro\\WB\\user.csv",sex+","+
                    info+","+ name+","+diqu+","+learn+","+work+","+ganqing+","+label+","+
                    daren+","+renzheng+","+renzhenginfo);


/*"性别":"女",
    "简介":"各种各样的蠢事，在每天阅读好书的作用下，仿佛烤在火上的纸一样渐渐燃尽。",
    "昵称":"尽弓刀OpS144",
    "code":0,
    "地区":"湖北"
*
*
*
* */


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

