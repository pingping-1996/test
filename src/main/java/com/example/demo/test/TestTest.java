package com.example.demo.test;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTest {
    public static void main(String[] args) {
        /*Date date = new Date();
        String strDateformat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateformat);
        System.out.println(sdf.format(date));*/
        /*String arry = "bbs({\"related\":[{\"hot\":2,\"id\":\"77E02C7CA5B36DEAF7B733856C73F460\",\"keyword\":\"大众飞利浦\",\"not_analyzed_keyword\":\"大众飞利浦\"},{\"hot\":1,\"id\":\"482D2E6C1C91EFA7A48873F0B85B876A\",\"keyword\":\"DS换飞利浦H7灯\",\"not_analyzed_keyword\":\"DS换飞利浦H7灯\"},{\"hot\":1,\"id\":\"D86FF7A91CFF59FB9CA72D177A45BE97\",\"keyword\":\"飞利浦胎压监测北京轮胎\",\"not_analyzed_keyword\":\"飞利浦胎压监测北京轮胎\"},{\"hot\":1,\"id\":\"F8FEBB7DD199B2B4497DE53B8DD2FCB9\",\"keyword\":\"道奇飞利浦\",\"not_analyzed_keyword\":\"道奇飞利浦\"}],\"bbsModelList\":{\"bbsList\":[{\"abstract_bbs\":\"原车的卤素大灯的灯光大家都懂的，最近这段时间京东车品节满200-减100优惠期间入手了<em class='red_color'>飞利浦</em>的极速光H7，换上先用用，以后换不换氙气大灯，等有米再说。<em class='red_color'>飞利浦</em>H7。车友们先来看看我的车子吧嘻嘻！\",\"author\":\"xuser10253467\",\"digest_flag\":\"0\",\"fourm_name\":\"卡罗拉论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180724/2018072417_cf6c556c48bcb1bf0538MCKJ3tv8xYFb.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_6ad528f9748d3165741aM7RGDaTdqMBS.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_1812641c353b631260fcziGHwuGnS3Ck.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_768690ab580dc585bc11tyXPbGvIBHIF.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_490d302f93d8fe06dd255nKJMVLirwaG.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_8c9730cb30c9bc99b867kjUjyFqHg50u.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_2f9a2331a07130c117812gN95cBqxWam.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_162bdb287d8d9784e4e0R9ypjw4C35CI.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_110b1750bbe29ec9452c2jJvairpzEZp.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_77ddd21f6e3ee094f85fPZctORdbxqZm.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33946455\",\"publish_time\":\"2018-07-24\",\"replies\":\"15\",\"special\":\"\",\"tid\":\"33946455\",\"title\":\"卡罗拉不拆大灯更换<em class='red_color'>飞利浦</em>极速光！\",\"views\":\"7443\"},{\"abstract_bbs\":\"17款科雷嘉更换4s店更换<em class='red_color'>飞利浦</em>led近光灯，一个月，昨天发现一侧大灯有肉眼能分辨出的频闪，请教是何问题？\",\"author\":\"WJZ5665mt\",\"digest_flag\":\"0\",\"fourm_name\":\"科雷嘉论坛\",\"picPost_url\":\"\",\"pic_flag\":\"0\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33926963\",\"publish_time\":\"2018-07-19\",\"replies\":\"12\",\"special\":\"\",\"tid\":\"33926963\",\"title\":\"科雷嘉4s店更换<em class='red_color'>飞利浦</em>led灯泡后频闪问题\",\"views\":\"3847\"},{\"abstract_bbs\":\"汉兰达的蜡烛灯众所周知，虽然不不怎么跑夜路，但几次夜路下来，远光尚可但近光确实感觉太差了，还不如跑了6年的高尔夫亮。考虑过改灯换总成，2000-6000块也不便宜，关键2年一上线确实麻烦。换灯泡也算一种方案，但是LED大灯品牌太杂，实在分不清好坏。偶然发现<em class='red_color'>飞利浦</em>LED大灯，某东自营三年质保，一直关注\",\"author\":\"黑虎还乡\",\"digest_flag\":\"0\",\"fourm_name\":\"汉兰达论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180619/2018061919_27e90b12477c563bd20bvs62C74snTZA.jpg;http://image.xcar.com.cn/attachments/a/day_180619/2018061919_4dd61b4454ac39b19582pWb30RrKGdWn.jpg;http://image.xcar.com.cn/attachments/a/day_180619/2018061919_7af6efd8f477c4ccc76bQkpKXk5LAGYu.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33703519\",\"publish_time\":\"2018-06-19\",\"replies\":\"10\",\"special\":\"\",\"tid\":\"33703519\",\"title\":\"换<em class='red_color'>飞利浦</em>LED近光作业\",\"views\":\"2927\"},{\"abstract_bbs\":\"12年买的起亚福瑞迪了，买车六年多了，小区没有地下停车库，重庆这天气日晒雨淋六年，还是没什么大毛病，就是漆面有点老化，车灯也越来越微弱了，电瓶倒是换了好几个。灯泡没换过，所以灯光效果还是达不到想要的标准，这次在网上买了一对<em class='red_color'>飞利浦</em>的灯泡就给换掉了。其实是京东搞活动满200送100买的，老婆给的零花钱少\",\"author\":\"xuser10253462\",\"digest_flag\":\"0\",\"fourm_name\":\"福瑞迪论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180724/2018072416_969e9bce7a703ccb84eaEaQ658qJJlGA.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_6c01969387039a10149c5MuItzSho5TC.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_7d45760789107c65ccfbcmfLlJkks6I1.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_16d15397a12381684c74sHn4ifulY8pv.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_956bdc9fef0fbae74f53rYkfRBSJnEc0.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_4b336a6be933c05292b9vdrzPc7wfy6J.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_bd567ae1e00b213f98561V52wJChiJDN.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_d880111f3e5fdb7631f7daIe7hKYDfYe.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_79d53383240216229eaeQ89CxOu8RiMu.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072416_01481507e5a5ecb5170exaAJLODoHmQQ.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33946184\",\"publish_time\":\"2018-07-24\",\"replies\":\"11\",\"special\":\"\",\"tid\":\"33946184\",\"title\":\"11款起亚福瑞迪六年更换<em class='red_color'>飞利浦</em>H7极速光大灯\",\"views\":\"6923\"},{\"abstract_bbs\":\"前段时间开车，突然发现车灯不亮了，怎么打都没得反应。好在当时路上没得什么车，还没出撒子事情哟。回到家里在车库停好车，赶紧上网买个新的。本来一开始想随便买个牌子就换了的，后来想起屋头有个<em class='red_color'>飞利浦</em>的灯\",\"author\":\"xuser10253520\",\"digest_flag\":\"0\",\"fourm_name\":\"重庆论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180723/2018072317_01e4d73c7d9c065989ddMkPy9K5jQCRD.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_0fc0fae77695b3dd750dZZE9P6szGLqg.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_e4a77fedb9faa2706d75mvkzJxT5B7rc.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_a907f4cfa142428514d7WAr8V7XoDoGP.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_254c544b59896dfa3c5bKH5OSzJyG2Ii.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_02d896f0900d0ebcd638xCGd7KZdOARD.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_46295e619652abd5aaef8RDLku5MLRnz.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_5c4b65023bc253ab3f0ep3PXpfg0ABuN.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_7a0393991352e4ca9c4407BFF4QbEoQe.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072317_24f4346cb7dc67137df5DNhPzDCx1Stw.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33942397\",\"publish_time\":\"2018-07-23\",\"replies\":\"11\",\"special\":\"\",\"tid\":\"33942397\",\"title\":\"本田车灯选择<em class='red_color'>飞利浦</em>极速光卤素H7，质量杠杠的\",\"views\":\"7140\"},{\"abstract_bbs\":\"。各种对比后，选择了这款<em class='red_color'>飞利浦</em>LED灯，车灯型号是H7的。本来还说好好学一下，怎么安装车灯，结果临时有事儿\",\"author\":\"穿越人海遇见\",\"digest_flag\":\"0\",\"fourm_name\":\"奥迪A4L论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180723/2018072318_a508948b0f65f87e52f0tdNkhYaUlAFb.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_4ae7f894b8a06cab9c5diTZccYZo2FLL.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_6b0831a362578deb27b9USMIoc83W4lH.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_293ca50e5d4287b1f554tSlQCyqLcKHD.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_6d061bfbbda646c008efOSkJVDY5iOWc.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_1c1f22d0fa2ad81aa360FaPi5DTcrdnn.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_33e2c1efac511c5b21452pMTlBFOn71B.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_89140c4030b488f1e854d9DLEmk49U5q.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_be3be5b9e47d13fbaff9xqcDpjUubhW6.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072319_e347dfc0257b4e56e53dgrTddDZlKE9x.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33942803\",\"publish_time\":\"2018-07-23\",\"replies\":\"11\",\"special\":\"\",\"tid\":\"33942803\",\"title\":\"小4“更新”<em class='red_color'>飞利浦</em>LED-H7车灯，闪闪发亮走一波~\",\"views\":\"4401\"},{\"abstract_bbs\":\"灯对面都不搭理你，所以很想换灯，现在LED灯又亮价格也适中，很适合我。看着别人车灯晚上很亮就想着把灯搞得亮点，所以网上买了LED的灯这大灯是<em class='red_color'>飞利浦</em>的LED灯，相信大家都听说过把。<em class='red_color'>飞利浦</em>还是大品牌，质量\",\"author\":\"xuser10253420\",\"digest_flag\":\"0\",\"fourm_name\":\"帕萨特论坛|帕协\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180724/2018072417_874ad2e8235311ee90f3YTZhWWlbzken.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_452d5a8c34c7260b3bb0U9ZHcnagxOla.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_76baa07fe8d84af55ab9zwwOJ57ruwud.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_387091f69a6f8ae2d1e0gkZv8rSrQU4e.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_d88cff7049a6a7bc1864PVFBla70TTx4.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_fe00aecc091bb036372cOEBcAdGfWmdf.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_2abf7da7bc900940c2cewZfLUXTTDDSI.jpg;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_ae73a5e3e969adc4cdeaAlTaz2woHnMH.png;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_b61ac0a438a310bc3a8euPoRWzYE3NFX.png;http://image.xcar.com.cn/attachments/a/day_180724/2018072417_ad8686b7159a0249f5c2eBW4z5sdmHYT.png\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33946615\",\"publish_time\":\"2018-07-24\",\"replies\":\"13\",\"special\":\"\",\"tid\":\"33946615\",\"title\":\"老帕萨特蜡烛灯更换<em class='red_color'>飞利浦</em>耀白光LDE大灯\",\"views\":\"4022\"},{\"abstract_bbs\":\"把卤素灯改成<em class='red_color'>飞利浦</em>led大灯要不要专用支架，还是用原来的卤素灯支架，改过led的同学回答下，谢谢！！\",\"author\":\"雨后阳光彩虹\",\"digest_flag\":\"0\",\"fourm_name\":\"科雷嘉论坛\",\"picPost_url\":\"\",\"pic_flag\":\"0\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33877482\",\"publish_time\":\"2018-07-16\",\"replies\":\"11\",\"special\":\"\",\"tid\":\"33877482\",\"title\":\"科雷嘉装<em class='red_color'>飞利浦</em>led大灯问题\",\"views\":\"2679\"},{\"abstract_bbs\":\"一直以来，都觉的起亚原厂卤素大灯不够亮，特别是开夜路，很影响驾驶安全，特别是遇到开远光的，自己的车灯太暗闪灯对面都不搭理你。所以很想换灯，实际上只是色温和照射面积带来的感觉而已，毕竟都是符合原厂标定的。我就是尝试换LED大灯，因为便宜，哈哈！！LED大灯特点：优点：色温高，看起来亮，点亮速度最快，适\",\"author\":\"xuser10253719\",\"digest_flag\":\"0\",\"fourm_name\":\"索兰托论坛\",\"picPost_url\":\"http://image.xcar.com.cn/attachments/a/day_180723/2018072318_31d2ad69d813ce84cebbrpJ6B4lCos7B.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_ab85b1499d4075f73a2c1eWPuqmCyaSn.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_53b62cfe3eccf445a36bUU4XzWSK4aEv.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_40de13c1184223ec472bJp4P6JtZLYvO.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_e9ab7d626d59aa8f162aXcbx72qaCayw.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_bbede910df09ea6806025TOvwhnhD0Xk.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_427ecaa72076b6e8d9e4GL7XpQyL9OkW.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_8a60d6abe4c00d1fc0d8LdB6p6yJf2cF.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_8335fac029f867eda00cuDdJ2OUaGHrJ.jpg;http://image.xcar.com.cn/attachments/a/day_180723/2018072318_86554df1dd92f16ed0e4ofVReTv4IlxH.jpg\",\"pic_flag\":\"1\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33942598\",\"publish_time\":\"2018-07-23\",\"replies\":\"14\",\"special\":\"\",\"tid\":\"33942598\",\"title\":\"索兰托蜡烛灯升级<em class='red_color'>飞利浦</em>LED耀白光大灯\",\"views\":\"9561\"},{\"abstract_bbs\":\"型号是<em class='red_color'>飞利浦</em>220xw8，分辨率1680*1050。屏幕没有任何坏点，外挂有正常使用痕迹，没有明显的划痕。南山福田坂田可以送货。pm或者加我qq吧13796052\",\"author\":\"pangzi85480\",\"digest_flag\":\"0\",\"fourm_name\":\"深圳跳蚤市场\",\"picPost_url\":\"\",\"pic_flag\":\"0\",\"post_url\":\"http://www.xcar.com.cn/bbs/viewthread.php?tid=33710557\",\"publish_time\":\"2018-06-21\",\"replies\":\"13\",\"special\":\"\",\"tid\":\"33710557\",\"title\":\"120出一个<em class='red_color'>飞利浦</em>22寸液晶显示器\",\"views\":\"2257\"}],\"totalPageNums\":3553,\"category\":{\"bbsCategory\":[\"精华帖\",\"提问帖\",\"投票帖\",\"活动帖\"],\"forumCategory\":[{\"fid\":\"23\",\"forumName\":\"汽车改装\"},{\"fid\":\"193\",\"forumName\":\"氙气大灯（HID)专区\"},{\"fid\":\"268\",\"forumName\":\"深圳跳蚤市场\"},{\"fid\":\"46\",\"forumName\":\"四川论坛\"},{\"fid\":\"91\",\"forumName\":\"北京论坛\"}]},\"categoryShow\":1,\"resultNums\":35525,\"spendTime\":0.028},\"status\":1})";
        String str1 = arry.substring(4,arry.length()-1);
        System.out.println(str1);*/
        /*String url = "http://sou.xcar.com.cn/XcarSearch/infobbssearchresult/bbs/" +
                "%E9%A3%9E%E5%88%A9%E6%B5%A6/none/none/none/none/3";
        //正则匹配到url中的页码
        String page = url.substring(url.length()-1);//
        System.out.println(page);
        String url2 = url.substring(0,url.length()-1);
        Integer i = Integer.valueOf(page);
        i = i + 1 ;
        String nextpage = url2+i;
        System.out.println(nextpage);*/

        /*作废
        *
        * //正则匹配到url中的页码
            String regEx1 = "none/(/d*)";
            //匹配到"page=数字&searchValue"
            Pattern pattern1 = Pattern.compile(regEx1);
            Matcher matcher1 = pattern1.matcher(url);
            String url2 = String.valueOf(matcher1.group());
            //再次匹配数字
            String regEx3 = "/d*";
            Pattern pattern3 = Pattern.compile(regEx3);
            Matcher matcher3 = pattern3.matcher(url2);
            Integer page = Integer.valueOf(matcher3.group());

            String regEx2 = "&searchValue=.*";
            Pattern pattern2 = Pattern.compile(regEx2);
            Matcher matcher2 = pattern2.matcher(url);
            String houzhui = String.valueOf(matcher2.group());
        *
        *  */
       /* String regEx1 = "none/\\d*";
        Pattern pattern1 = Pattern.compile(regEx1);
        Matcher matcher1 = pattern1.matcher(url);
        if(matcher1.find()){
            String url2 = String.valueOf(matcher1.group());
            System.out.println(url2);
        }
*//*
        try{
            String getcode = getCookie("discuz_collapse");
            System.out.println("jsadhjkdhklsal============"+getcode);
        }catch (Exception e){
            e.printStackTrace();
        }*/
       /* String cookie = "";
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod("http://www.xcar.com.cn/bbs/viewthread.php?tid=34129412&page=2");
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        try {
            client.executeMethod(get);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //Cookie[] cookies = client.getState().getCookies();

        //if (cookies.length == 0) {

        //System.out.println("None");

        //} else {

        //for (int i = 0; i < cookies.length; i++) {

        //System.out.println(cookies.length);

        //String a = cookies[i].toString();

        //cookie += a;

        //System.out.println(a);

        //}

        //}

        String html = gethtml("http://www.xcar.com.cn/bbs/viewthread.php?tid=80789399");
        System.out.println(html);
        Document doc = Jsoup.parse(html);

        //帖子主题    √
        String title = doc.select(".maintop .title").text();
        System.out.println(title);
        String title2 = title.substring(title.indexOf(" > ")).replace(" > ","");
        System.out.println("yuyuyuyuyuyuyuyuyuyu========"+title2);

        //当前页url   √
        //String url = "在传参中获得";
        //评论的下一页任务  tasks 在当前url+1;
        //楼主经验值在第一页循环第一条 //type  //主贴所有图片链接
        //循环
        Elements elements = doc.select(".main");
        String huifulink;
        for(Element element:elements){
            //各层等级
            String dengji = element.select(".starname span").text();
            //各层经验值 需要判断是否为空 取不到alt属性
            String jingyan = element.select(".otherinfo .star .img").attr("alt");
            System.out.println(jingyan);
            //楼层
            String louceng = element.select(".t_number").text();
            //发表时间  判断 截取  正则匹配不上
            String time = element.select(".mainboxTop p").text().replace("发表于 ","");
            String regex = "\\d+-\\d+-\\d+ \\d+:\\d+";
            Pattern pattern4 = Pattern.compile(regex);
            Matcher matcher4 = pattern4.matcher(time);
            if(matcher4.find()){
                System.out.println("aaa");
                System.out.println("时间是："+matcher4.group());
            }
            //回复人
            String name = element.select(".name a").text();
            //回复内容  replys   有问题
            String connect = element.select(".t_msgfont1").text();
            //回复人链接
            huifulink = element.select(".avatar a").attr("href");

            System.out.println("循环输出信息："+ "等级："+dengji +"经验值："+jingyan +"楼层："+louceng +
                    "发表时间："+time +"回复人："+name +"回复内容："+connect +"回复人链接："+huifulink);

        }

 //判断是否为首页 首页取楼主信息
        String num1 = doc.select(".fn_0209 .active").text();
        System.out.println(num1);
        String huifu = "";
        if(num1.equals("1 1")){
            //查看数      √ （只有第一页有浏览数）
            String chakan = doc.select(".mainboxTop span").text();
            String chakan2 = chakan.substring(3,chakan.indexOf("|"));
            System.out.println(chakan2);
            //主贴回复数  √ (只有第一页有回复数)
            huifu = doc.select(".mainboxTop span").text();
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(huifu);
            if(matcher.find()){
                System.out.println("字符串sa是以数字结尾的，结尾的数字是："+matcher.group());
            }
            String huifu2 = String.valueOf(matcher.group());
            System.out.println("yyy");
            String dengji111 = doc.select(".starname span").get(0).text();//等级
            //String jingyan111 = doc.select(".otherinfo .star .img").get(0).text();//经验
            String time111 =  doc.select(".mainboxTop p").get(0).text().replace("发表于 ","");
            String connect111 = doc.select(".t_msgfont1").get(0).text();
            String name = doc.select(".name a").get(0).text();  //作者
        }
        String luntan = doc.select(".titleStyle1 h3 a").get(2).text();
        System.out.println("什么论坛："+luntan);

        String url = "http://pv.xcar.com.cn/0.gif?$http://www.xcar.com.cn/bbs/viewthread.php?tid=80789399&page=1";
        String url222 = url.substring(0,url.indexOf("&page="));
        System.out.println("222222"+url222);
        String page = url.substring(url.length()-1);//
        System.out.println(page);
        String jjj= doc.select(".fn_0209 a").attr("class");
        System.out.println("jjjjjj"+jjj);

        String last = doc.select("a.page_no").attr("class");
        System.out.println("last=========================="+last);
        if(last.equals("page_no")){
            System.out.println("最后一页");
        }else{
            System.out.println("不是最后一页");
        }

        String num2 = doc.select(".fn_0209 .page").text();
        System.out.println(num2);
        if(num2.equals("")){
            System.out.println("就一页");
        }


      }





    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        //request.setHeader("Accept",
              //  "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        request.setHeader("Accept-Encoding", "gzip, deflate");
//        request.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
         request.setHeader("Cookie",
                 "TY_SESSION_ID=b21ca37f-3b35-4fef-92aa-1d6927bf528a; _Xdwuv=5c48251ebef16; _Xdwnewuv=1; _PVXuv=5c48a80d413fc; _fwck_www=8467c18e04e9cfa191c9e89ef7c68786; _appuv_www=4e25489e00ce39894493853d4ec9411b; _fwck_my=e4b214cdf4d2e271930b8237cd9b6826; _appuv_my=47bb852d5a5829288037166484125771; bbs_cookietime=31536000; _locationInfo_=%7Burl%3A%22http%3A%2F%2Fshenyang.xcar.com.cn%2F%22%2Ccity_id%3A%2263%22%2Cprovince_id%3A%226%22%2C%20city_name%3A%22%25E6%25B2%2588%25E9%2598%25B3%22%7D; " );
        //request.setHeader("Referer","http://www.xcar.com.cn/bbs/viewthread.php?tid=33946455");
//        request.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //request.setHeader("X-Requested-With", "XMLHttpRequest");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }


    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private static String getCookie(String name) throws Exception{
        //engine.eval(script);
        engine.put("document",  Jsoup.parse(new URL("http://www.xcar.com.cn/bbs/viewthread.php?tid=80789399"),30000));
        engine.eval("function getCookie(name) {\n" +
                "        var cookieName = encodeURIComponent(name) + \"=\",\n" +
                "            cookieStart = document.cookie.indexOf(cookieName),\n" +
                "            cookieValue = null;\n" +
                "        if (cookieStart > -1) {\n" +
                "            var cookieEnd = document.cookie.indexOf(\";\", cookieStart);\n" +
                "            if (cookieEnd == -1) {\n" +
                "                cookieEnd = document.cookie.length;\n" +
                "            }\n" +
                "            cookieValue = decodeURIComponent(document.cookie.substring(cookieStart + cookieName.length, cookieEnd));\n" +
                "        }\n" +
                "        return cookieValue;\n" +
                "    }");

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("getCookie", name);
    }


}
