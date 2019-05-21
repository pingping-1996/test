package com.example.demo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Tshuangseqiu {

    public static void main(String[] args) {
        File file = new File("D://excel/shaxuan.txt");
        List<String> list = txt2String(file);
        for (int code = 0; code <= list.size(); code++){

        }
    }



    public static List<String> txtString(File file){
        List list  = new ArrayList();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                list.add(s);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
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
}
