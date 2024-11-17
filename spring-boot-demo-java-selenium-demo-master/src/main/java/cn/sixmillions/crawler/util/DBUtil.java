package cn.sixmillions.crawler.util;

import java.sql.*;

/**
 * Java中使用JDBC连接数据库
 * 1. 加载驱动
 * 2. 创建数据库连接
 * 3. 创建执行sql的语句
 * 4. 执行语句
 * 5. 处理执行结果
 * 6. 释放资源
 *
 * @author lbw
 */
public class DBUtil {

    /**
     * Statement 和 PreparedStatement之间的关系和区别.
     * 关系：PreparedStatement继承自Statement,都是接口
     * 区别：PreparedStatement可以使用占位符，是预编译的，批处理比Statement效率高
     */
    public static void connDemo() {
//        String URL = "jdbc:mysql://127.0.0.1:3306/test_db?characterEncoding=utf-8";
//        String USER = "root";
//        String PASSWORD = "123456";
        String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String USER = "postgres";
        String PASSWORD = "000000";
        // 1.加载驱动程序
        try {
            //老版本驱动
            //Class.forName("com.mysql.jdbc.Driver");
            //mysql新版驱动
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "sixmillions";
            //预编译
            String sql = "select * from student where name=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("age"));
            }
            // 关闭资源
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connDemo();
    }

    public static Connection connPg() {
        String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String USER = "postgres";
        String PASSWORD = "000000";
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
