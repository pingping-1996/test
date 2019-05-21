package com.example.demo.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TXXW {

    public static void main(String[] args) throws HttpProcessException {
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                //.cookie("JUTE_SESSION_ID=86fdcfa8-bd6f-4a81-a11e-e2845ed929c0; JUTE_TOKEN=9b6ef6e5-749f-4100-b0ba-a5380fd62881; dxy_da_cookie-id=b7e8e76b6fa84c9084130afdd23f005b1555999061068; cms_token=0f20827b-874a-4861-b421-ac39c315aa83; DXY_USER_GROUP=49; __asc=d3ea7a5016a48c57859a8fe3c91; __auc=d3ea7a5016a48c57859a8fe3c91; Hm_lvt_8a6dad3652ee53a288a11ca184581908=1555999062; __utma=1.1229777727.1555999063.1555999063.1555999063.1; __utmc=1; __utmz=1.1555999063.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; _ga=GA1.2.1229777727.1555999063; _gid=GA1.2.458388678.1555999077; JUTE_BBS_DATA=d3ee9fb78f8c846f767da2861a2b20ff5f49326234ec1f254a42029e68ab3dc84d823ac50eecc3cebb3db1f5f424c0d350f29443ec23951dbd81373af8b36bbba354f7747bb06bd45cdffb238530f057; _gat=1; CMSSESSIONID=E49692C7893B41A184F9472BCBB6C998-n2; bannerData={\"banner\":false,\"message\":\"不显示banner\"}; Hm_lpvt_8a6dad3652ee53a288a11ca184581908=1555999179; JUTE_SESSION=a8ec31558e78c6f7c6c93b4f0162a4ecfc47a3704d751a743638d4fa91ca036160c12262d3b50a94a2958c7851a6d926a7bb3e16b871c4baf966c11484527fc2f9ea8e6ad827107f; __utmb=1.13.9.1555999193859")

                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)  //设置headers，不需要时则无需设置
                .url("https://pacaio.match.qq.com/irs/rcd?cid=146&token=49cbb2154853ef1a74ff4e53723372ce&ext=ent&page=749").encoding("GBK"));
        System.out.println(html);

        JSONObject jsonObject = JSON.parseObject(html);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        //System.out.println(jsonArray);
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            System.out.println((i+1)+"==="+jsonObject1.getString("title"));
            System.out.println((i+1)+"==="+jsonObject1.getString("update_time"));

        }
     }




}
