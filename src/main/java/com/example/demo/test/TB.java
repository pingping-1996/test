package com.example.demo.test;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TB {
    public static void main(String[] args) throws UnsupportedEncodingException {
        /*读取excel表格中的所有关键字*/
        File file = new File("D:\\TB\\TB.txt");
        List<String> list = txt2String(file);
        int j = 2677;
        String sql = "";

        for (String item : list) {

            String zhuanma = getURLEncoderString(item);
            /*sql = "INSERT INTO newslist_000  (`siteID`, `cid`, `channelName`, `keyword`, `pageTypeID`, `weight`, `url`, `pageIdx`, `nextPageTime`, `status`, `intv`, `attachTag`, `lastcrawltime`, `nextcrawltime`, `createTime`, `modiTime`)" +
                    "VALUES (" + 261 +", "+"'Nwangdaitianyan'"+ ", "
                    +"'网贷天眼'"+", " + "'" + item + "'" + ", " +176+", " + 1 + ", "
                    +"'https://www.p2peye.com/search.php?mod=zonghe&srchtxt=" + item
                    //+"'http://www.haibao.com/search/index.html?name=" + item+"&type=2&orderType=1&isHighLight=1"
                    +"'"+", "+ 1 +", " +"-1"+ ", " + 0 +", "+ 86400 +", "
                    +"'{\"listbrand\":\"" + item + "\",\"project_name\":\"ebx\"}',"
                    +"NULL, '2018-03-25 14:23:32', '2018-03-14 11:35:35', '2018-03-24 14:23:32');";*/
            sql = "INSERT INTO bbslist_000(`siteID`, `cid`, `tname`, `keyword`, `pageTypeID`,`weight`, `url`, `pageidx`, `status`, `intv`, `lastcrawltime`,`nextcrawltime`, `createtime`, `moditime`)"
                    +"VALUES (" + 267 +", "+"'Bxcar'"+ ", "
                    +"'爱卡汽车'"+", " + "'" + item + "'" + ", " +153+", " + 1 + ", "
                    +"'http://sou.xcar.com.cn/XcarSearch/infobbssearchresult/bbs/" + item+"/none/none/none/none/1"
                    +"'"+", "+ 1 +", " + 0 +", "+ 7281 +", "
                    +"NULL, '2018-03-25 14:23:32', '2018-03-14 11:35:35', '2018-03-24 14:23:32');";

            /*INSERT INTO ``(`siteID`, `cid`, `tname`, `keyword`, `pageTypeID`,
             `weight`, `url`, `pageidx`, `status`, `intv`, `lastcrawltime`,
              `nextcrawltime`, `createtime`, `moditime`) VALUES
               (222, 'Bpcbaby', '太平洋宝贝', '欧莱雅', 153, 1,
               'https://ks.pcbaby.com.cn/kids_bbs.shtml?q=%C5%B7%C0%B3%D1%C5', 1, 0, 7281,
               NULL, '2019-03-27 12:17:47', '2017-12-14 14:51:08', '2019-03-27 10:17:09');
            *
            * */
            j++;
            contentToTxt("d://TB/TBTB.sql",sql);
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

    /*
     * url编码
     * */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /*
     * 解码
     * */
    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
