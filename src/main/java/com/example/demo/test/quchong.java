package com.example.demo.test;

import java.io.*;
import java.util.*;
/*
* 利用set集合无重复数据特性去重
*
* */
public class quchong {
    public static void main(String[] args){
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        File file = new File("D://excel/shaxuan.txt");
        List<String> list = txt2String(file);
        for (int code = 0; code <= list.size(); code++){
            String str = list.get(code).toString();
            List<String> typelist = Arrays.asList(str.split("/"));
            String clearstr = removeDuplicate(typelist);
            System.out.println(str);
            contentToTxt("d://excel/cleartype.txt",
                    clearstr);
        }
    }
    /*
    * 去重
    *
    * */
    public static String removeDuplicate(List list) {
        Set<String> set = new HashSet<String>(list);
        // 得到去重后的新集合
        List<String> newList = new ArrayList<String>(set);
        String str = "";
        for(int k = 0; k< newList.size();k++){
            str+=(newList.get(k)+"、");
        }
        return str;
    }
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
