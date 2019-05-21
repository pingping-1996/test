package com.example.demo.test;

import java.sql.*;
import java.util.Scanner;

public class wenwen {
    public static void main(String[] args)   {
        Scanner sc = new Scanner(System.in);
        System.out.println("你好~");

        while(true) {
            String ques = sc.nextLine();

            //怎么样？不怎么样- 还行吧- 很一般-  还真挺好-
            int shu = (int) (1 + Math.random() * 10);
            if (ques.contains("怎么样")) {
                System.out.println("--- ---- ---");
                if (shu == 1 || shu == 5) {
                    System.out.println("不怎么样");
                } else if (shu == 2 || shu == 6) {
                    System.out.println("还行吧");
                } else if (shu == 3 || shu == 7) {
                    System.out.println("很一般");
                } else if (shu == 4 || shu == 8) {
                    System.out.println("还真挺好");
                } else {
                    System.out.println("你觉得怎么样就怎么样");
                }
            }
            if (ques.contains("什么")&&!ques.contains("为")) {
                System.out.println("--- ---- ---");

                System.out.println("不要问我啊~");
            }
            if (ques.contains("为什么")){
                if (shu == 1 || shu == 5) {
                    System.out.println("因为......" +
                            "");
                    System.out.println("我再想想吧");
                } else if (shu == 2 || shu == 6) {
                    System.out.println("哪有那么多为什么");
                } else if (shu == 3 || shu == 7) {
                    System.out.println("你是百万个为什么啊");
                } else if (shu == 4 || shu == 8) {
                    System.out.println("我知道但我拒绝回答~");
                } else {
                    System.out.println("这个问题让我无语！");
                }
            }            //
            if("拜拜".equals(ques)){
                System.out.println("--- ---- ---");

                System.out.println("拜拜喽~");
                break;
            }
        }

    }
        public void wenwen() throws SQLException
        {
            Connection connection=null;
            PreparedStatement ps=null;
            ResultSet resultSet=null;
            try{
                String driverClassName="com.mysql.jdbc.Driver";

                String url="jdbc:mysql://localhost:3306/exam";

                String username="root";

                String password="123";

                Class.forName(driverClassName);

                connection=DriverManager.getConnection(url,username,password);
                /*
                 * 创建statement
                 */
                String sqlString="select * from emp where ques = ?";

                ps=connection.prepareStatement(sqlString);


                resultSet=ps.executeQuery(sqlString);

                ps.setObject(1,"text");
                /*
                 *
                 * 循环遍历rs ,打印其中数据
                 */
                while(resultSet.next())
                {
                    System.out.println(resultSet.getString(1)+","+resultSet.getString(2));
                }
            }catch(Exception e)
            {
                throw new RuntimeException(e);
            }
            finally{
                //关闭资源
                if(resultSet!=null) resultSet.close();
                if(ps!=null) ps.close();
                if(connection!=null) connection.close();
            }
        }



}
