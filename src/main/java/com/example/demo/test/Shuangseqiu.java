package com.example.demo.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Shuangseqiu {

    public static void main(String[] args) {
        int a[] = new int[34];
        int j = 0;
        Random random = new Random();
        for(int z=0 ; z<5; z++){
            List<Integer> list = new ArrayList();
            for(int i = 0;i<=32;i++){
                a[i]  = random.nextInt(34);
                if(!(a[i]==0)){
                    list.add(a[i]);
                }
                //System.out.println("随机数====="+a[i]);

                System.out.println(list);
                Set<Integer> set = new HashSet<Integer>(list);
                int blibli = random.nextInt(15)+1;
                if(set.size()==6){
                    List<Integer> newList = new ArrayList<Integer>(set);
                    Collections.sort(newList);
                    System.out.println("结果======"+newList);
                    contentToTxt("d://TB/双色球.txt", newList , blibli);
                    break;
                }
            }
        }
    }
    /*
     * 写入文档
     * */
    public static void contentToTxt(String filePath, List content,int content2) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                    filePath), true));
            writer.write(content + "——————"+ content2 + "\n" );
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

