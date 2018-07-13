package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by LSC333 on 2018/4/2.
 */
public class DBUtil {
    private static String URL="jdbc:mysql://localhost:3306/test";
    private static String USERNAME="root";
    private static String PASSWORD="lihaoyuan";
    private static Connection conn=null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }
}
