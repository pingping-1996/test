package com.example.demo.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test2 {
    public static void main(String[] args) {

        String url = "https://china.chinadaily.com.cn/5bd5639ca3101a87ca8ff636/page_2.html";
        String pattern = "page_(\\d+).html";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        String last2 = "";
        String last1 = "";

        if(m.find()){
            last1 = m.group(1);
            last2 = m.group();
            //String nextpage = ""+last+".html";
            System.out.println(last1+last2);
        }
    }
}

