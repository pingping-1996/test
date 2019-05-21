package com.example.demo.elong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class yilongfangxing {
//C:\Users\BFD-LT\Desktop\fangxing.json
    //对文件名称截取  hotelid
    public static void main(String[] args) {
        List<String> list = getFileList("D:\\baifendian\\yilong_fangxing");

        for(String lists:list){
            System.out.println("路径名字"+lists);
            //获取hotelid
            String hotelid = getStrByPrePost(lists,"D:\\baifendian\\yilong_fangxing\\",".json");
            System.out.println(hotelid);
            String url = "http://hotel.elong.com/"+hotelid+"/";

            JSONArray reviewJSONARRY = new JSONArray();
            JSONObject fangxingJson = new JSONObject();//创建房型对象

            String comment = readJsonData(lists);
            //System.out.println(comment);

            try{
                reviewJSONARRY.addAll(getfangxingJSON(comment, url));
            }catch(Exception e){
                e.printStackTrace();
            }
            fangxingJson.put("reviewArry",reviewJSONARRY);

             contentToTxt("d://yilong/fangxing20190415.json",
                    fangxingJson.toJSONString());// 保存.json
        }
    }
    /*
    * 读取当前的一个文件
    *
    * */
    public static String readJsonData(String pactFile) /*throws IOException */{
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile); //"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("没找到文件" + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
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
    //房型抓取
    public static JSONArray getfangxingJSON(String posthtml, String url) {
        JSONObject jsonObject = JSON.parseObject(posthtml);
        String hotelInventory = jsonObject.getString("hotelInventory");//
        JSONObject hotelInventorys = JSON.parseObject(hotelInventory);//
        System.out.println(url);
        JSONArray rooms = hotelInventorys.getJSONArray("rooms");//?
        JSONArray roomsArry = new JSONArray();
        System.out.println("349行"+rooms.size());
        for(int i=0;i<rooms.size();i++){
            JSONObject roomss=rooms.getJSONObject(i);
            JSONObject roomsObj = new JSONObject();
            //关联url
            roomsObj.put("OBJ_URL3",url);
            System.out.println("关联="+i);
            //房间名称	RM_TIT	豪华双床间
            //roomsObj.put("RM_TIT",roomss.getString("roomTypeName"));
            //房间总描述	RM_INTRO	房间38㎡ 大/双床可住：2 等
            roomsObj.put("RM_INTRO",
                    "房间："+roomss.getString("area")+"m^2"+
                            "|"+roomss.getString("bedTypeName")+
                            "|可住："+roomss.getString("maxPersonOfRoom")+
                            "|楼层："+roomss.getString("floor")+
                            "|"+roomss.getString("allRoomFreeNetworkDesc"));
            //房间最低价格	RM_BASEPRICE
            JSONArray products = roomss.getJSONArray("products");
            String picurl = products.getJSONObject(0).getString("avgPrice");
            roomsObj.put("RM_BASEPRICE",picurl);
            //产品数量	RM_SKU_SUM	共9个产品
            roomsObj.put("RM_SKU_SUM", "共"+products.size()+"个产品");
            JSONArray yuding = new JSONArray();
            String zhifus = null;
            String prv = null;
            for(int j=0;j<products.size();j++){
                prv = "第"+(j+1)+"个产品";
                JSONObject yudings = new JSONObject();
                JSONObject yudingsss = products.getJSONObject(j);
                String cancelRuleDesc = yudingsss.getString("cancelRuleDesc");
                JSONArray zhifu = yudingsss.getJSONArray("productAttachDesc");

                for(int k=0;k<zhifu.size();k++){
                    //roomsObj.put("RM_SKU_NUM","第"+(k+1)+"个产品");
                    //roomsObj.put("RM_TIT",roomss.getString("roomTypeName"));
                    JSONObject zhifuss = zhifu.getJSONObject(k);
                    String key = zhifuss.getString("key");
                    if(key.equals("支付方式")){
                        zhifus = zhifuss.getString("value");
                    }
                }
                //第几个产品
                yudings.put("RM_SKU_NUM", prv);
                //产品归属名称
                yudings.put("RM_TIT",roomss.getString("roomTypeName"));
                //支付方式	RM_PAYINFO	预定/在线付
                yudings.put("RM_PAYINFO",zhifus);
                //预定规则	RM_BOOKRULE	不可取消
                yudings.put("RM_BOOKRULE",cancelRuleDesc);
                //////////////////
                /*JSONObject yudinggs = new JSONObject();
                if(yudings.equals("订单一经确认成功，不可取消或变更；如未能如约入住，您的预付房费将不予退还。")){
                    yudinggs.put("RM_BOOKRULE","不可取消");
                }else if(yudings.equals("订单提交后可随时免费取消/变更。")){
                    yudinggs.put("RM_BOOKRULE","免费取消");
                }*/
                yuding.add(yudings);
                roomsObj.put("RM_PAYINFO_PAYINFO",yuding);
            }
            //数据抓取时间
            roomsObj.put("RM_TODAY","2019-04-15");
            roomsArry.add(roomsObj);
        }
        return roomsArry;
    }
    /*
    * 截取
    *
    * */
    public static String getStrByPrePost(String str, String pre, String post) {
        if (str != null) {
            if (pre != null) {
                int s = str.indexOf(pre);
                if (s > -1) {
                    str = str.substring(s + pre.length(), str.length());
                } else {
                    return null;
                }
            }
            if (post != null) {
                int e = str.indexOf(post);
                if (e > -1) {
                    str = str.substring(0, e);
                } else {
                    return null;
                }
            }
        }
        return str;
    }
}
