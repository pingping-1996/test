package com.example.demo.test;

import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.*;
/*
*
* 找到重复的元素并输出
* */
public class zhaochong {

    public static void main(String[] args) {
        /*
        * 找出每一行相同的元素
        *
        * */
        File file = new File("D:\\TX\\tx.txt");
        List<String> list2 = txt2String(file);

        for (String item : list2) {
            String str3 = item;
            String[] Str3Array = str3.split("\\，");
            List<String> list = new ArrayList<>();
            for(int i = 0 ;i<Str3Array.length;i++){
                list.add(Str3Array[i]);
            }

            Set<String> set = new HashSet<>(list);
            System.out.println("---------------------------------------");
            //获得list与set的差集
            Collection rs = CollectionUtils.disjunction(list,set);
            String rs2 = rs.toString();
            contentToTxt("d://TX/tx2.txt", rs2);
            System.out.println(rs);
            //将collection转换为list
        /*List<String> list1 = new ArrayList<>(rs);
        for(String str:list1){
            System.out.println(str);
        }
        */
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
