package com.example.demo.elong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YLxianggang {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\BFD-LT\\Desktop\\demo\\src\\main\\java\\com\\example\\demo\\elong\\fangxing.json");
        List<String> list = txt2String(file);
        JSONArray roomsArry = new JSONArray();
        for (String posthtml : list) {
            JSONObject jsonObject = JSON.parseObject(posthtml)
                    .getJSONObject("data").getJSONObject("roomList")
                    .getJSONObject("first");
            Set iii = jsonObject.keySet();
            JSONObject roomsObjs  = new JSONObject();
            for(Object key : iii){
               JSONArray jsonArrays = new JSONArray();
               JSONObject jsonObject3  = new JSONObject();

               JSONObject jsonObject2 = jsonObject.getJSONObject(key.toString())
                    .getJSONObject("providerLogoInfo");
                String mingcheng = jsonObject2.getString("mRoomName");
                String zuidi = jsonObject2.getString("lowestSubRoomPrice");
                //System.out.println("==="+mingcheng+zuidi);
                JSONArray jsonArray = jsonObject.getJSONObject(key.toString())
                        .getJSONArray("providerRoomInfo");
                String chuang = jsonObject.getJSONObject(key.toString())
                        .getJSONArray("providerRoomInfo")
                        .getJSONObject(0).getJSONObject("facilityInfo")
                        .getJSONArray("bedType").getJSONObject(0)
                        .getString("name");
                String people = jsonObject.getJSONObject(key.toString())
                        .getJSONArray("providerRoomInfo")
                        .getJSONObject(0).getJSONObject("personInfo")
                        .getJSONObject("personHold").getString("maxPersonNum");
                String wangluo = jsonObject.getJSONObject(key.toString())
                        .getJSONArray("providerRoomInfo")
                        .getJSONObject(0).getJSONObject("facilityInfo")
                        .getString("internet");
                String RM_INTRO  = "房间：" +""
                        + "|" + chuang+"|"+
                        "可住："+people+"|" +
                        "楼层："+"" +"|"+
                        wangluo;
                System.out.println(RM_INTRO);

                //房间  ""
                //jsonObject1  什么床
                //可住    几人
                //楼层  ""
                //包含无线  可以  facilityInfo


                String prv = null;
                jsonObject3.put("RM_BASEPRICE",zuidi);//价格
                jsonObject3.put("RM_TODAY","2019-04-15");//date
                jsonObject3.put("OBJ_URL3","http://hotel.elong.com/53201163/");//url
                jsonObject3.put("RM_SKU_SUM","共"+jsonArray.size()+"个产品");//ur
                jsonObject3.put("RM_INTRO",RM_INTRO);//ur

                for (int i = 0;i<jsonArray.size();i++){
                    JSONObject roomsObj2 = new JSONObject();
                    prv = "第"+(i+1)+"个产品";
                    JSONObject fangxing = jsonArray.getJSONObject(i);
                    //String name = fangxing.getString("roomNameCn");
                    //System.out.println("酒店名称==="+name);
                    String desc = fangxing.getJSONObject("policyService")
                            .getString("cancellationShowDesc");

                    //System.out.println("酒店描述==="+desc);
                    roomsObj2.put("RM_SKU_NUM", prv);
                    roomsObj2.put("RM_BOOKRULE",desc);
                    roomsObj2.put("RM_TIT",mingcheng);
                    roomsObj2.put("RM_PAYINFO","");

                    jsonArrays.add(roomsObj2);
                }
                jsonObject3.put("RM_PAYINFO_PAYINFO",jsonArrays);//
                roomsArry.add(jsonObject3);

             }

            System.out.println("jieguo==="+roomsArry);

        }
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
    /*
     * 遍历本地文件获取文件名
     * */
    public static List<String> getFileList(String strPath) {
        List<String> filelist = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        String filelistt = null;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                    System.out.println("文件还是文件夹1");
                } else if (fileName.endsWith("json")) { // 判断文件名是否以.json结尾
                    System.out.println("文件还是文件夹2");
                    //String strFileName = files[i].getAbsolutePath();
                    //System.out.println(strFileName);
                    filelistt = String.valueOf(files[i]);
                    filelist.add(filelistt);
                    //readJsonData(filelistt);
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }

    public static String readJsonData(String pactFile) /*throws IOException */ {
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile); //"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("没找到文件" + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return strbuffer.toString();
    }
}