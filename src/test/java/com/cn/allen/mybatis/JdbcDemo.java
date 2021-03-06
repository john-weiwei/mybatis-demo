package com.cn.allen.mybatis;

import com.cn.allen.mybatis.entity.TUser;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:ZhangWeiWei
 * @Date:2021/3/12
 * @Description:
 */
public class JdbcDemo {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "unitimes";

    @Test
    public void QueryPreparedStatementDemo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        List<TUser> users = new ArrayList<TUser>();
        try {
            // STEP 2: 注册mysql的驱动
            Class.forName("com.mysql.jdbc.Driver");

            // STEP 3: 获得一个连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: 创建一个查询
            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM t_user where userName= ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "lison");
            System.out.println(stmt.toString());//打印sql
            ResultSet rs = stmt.executeQuery();

//			ResultSet rs = stmt.getResultSet();


            // STEP 5: 从resultSet中获取数据并转化成bean
            while (rs.next()) {
                System.out.println("------------------------------");
                // Retrieve by column name
                TUser user = new TUser();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setRealName(rs.getString("realName"));
                user.setSex(rs.getByte("sex"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setNote(rs.getString("note"));

                System.out.println(user.toString());

                users.add(user);
            }
            // STEP 6: 关闭连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("-------------------------");
        System.out.println("there are "+users.size()+" users in the list!");
    }



    @Test
    public void QueryStatementDemo() {
        Connection conn = null;
        Statement stmt = null;
        List<TUser> users = new ArrayList<TUser>();
        try {
            // STEP 2: 注册mysql的驱动
            Class.forName("com.mysql.jdbc.Driver");

            // STEP 3: 获得一个连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: 创建一个查询
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String userName = "lison";
            String sql="SELECT * FROM t_user where userName='"+userName+"'";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(stmt.toString());


            // STEP 5: 从resultSet中获取数据并转化成bean
            while (rs.next()) {
                System.out.println("------------------------------");
                // Retrieve by column name
                TUser user = new TUser();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setRealName(rs.getString("realName"));
                user.setSex(rs.getByte("sex"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setNote(rs.getString("note"));

                System.out.println(user.toString());

                users.add(user);
            }
            // STEP 6: 关闭连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("-------------------------");
        System.out.println("there are "+users.size()+" users in the list!");
    }



    @Test
    public void updateDemo(){
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: 注册mysql的驱动
            Class.forName("com.mysql.jdbc.Driver");

            // STEP 3: 获得一个连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: 关闭自动提交
            conn.setAutoCommit(false);

            stmt=conn.createStatement();


            // STEP 5: 创建一个更新
            System.out.println("Creating statement...");
            String sql1 = "update t_user  set mobile= '13125858455' where userName= 'lison' ";
            String sql2 = "insert into t_user ( userName) values ('deer')";
            stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            System.out.println(stmt.toString());//打印sql
            int[] executeBatch = stmt.executeBatch();
            System.out.println("此次修改影响数据库的行数为："+ Arrays.toString(executeBatch));

            // STEP 6: 手动提交数据
            conn.commit();

            // STEP 7: 关闭连接
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            try {
                conn.rollback();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}