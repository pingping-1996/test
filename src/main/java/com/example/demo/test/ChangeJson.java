package com.example.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeJson {


    public static void main(String[] args) {


        File file = new File("D:\\BFD\\test\\学习读本.txt");
        List<String> list = txt2String(file);
        for (String item : list) {
            //System.out.println("item"+item);
            JSONObject jsonObject = JSON.parseObject(item);
            String url = jsonObject.getString("url");

            String pattern = "/(c_\\d+).htm";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(url);
//    "url":"http://news.xinhuanet.com/politics/leaders/2017-11/07/c_1121914725.htm",
            String news_id = "";
            if(m.find()){
                news_id = m.group(1);
                System.out.println("news_id==="+news_id);
                jsonObject.put("news_id",news_id);

                JSONObject attachtag = jsonObject.getJSONObject("attr")
                        .getJSONObject("attachtag");
                attachtag.put("project_name","媒体校对数据需求");
                System.out.println(jsonObject);
                item = jsonObject.toString();
            }

            contentToTxt("D:\\BFD\\test\\test.txt",item);

        }

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
    /*
     * 按行读取数据
     * */
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
