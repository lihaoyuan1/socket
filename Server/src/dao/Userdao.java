package dao;

import db.DBUtil;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LSC333 on 2018/4/2.
 */
public class Userdao {
    public boolean addUser(User user) throws SQLException {
        Connection conn= DBUtil.getConnection();
        String sql_1="SELECT 1 FROM user "
                +" WHERE user_name=?";
        PreparedStatement ptmt_1=conn.prepareStatement(sql_1);
        ptmt_1.setString(1, user.getUser_name());
        ResultSet rs=ptmt_1.executeQuery();
        if(rs.next()){
            return false;
        }
        String sql=" INSERT INTO user( user_name, password ) "
                +" VALUES (?, ?) ";
        PreparedStatement ptmt_2=conn.prepareStatement(sql);
        ptmt_2.setString(1, user.getUser_name());
        ptmt_2.setString(2, user.getPassword());
        ptmt_2.execute();
        return true;
    }
    public boolean delUser(String name) throws SQLException {
        Connection conn= DBUtil.getConnection();
        String sql_1=" SELECT id FROM user "
                +" WHERE BINARY user_name=?";
        PreparedStatement ptmt_1=conn.prepareStatement(sql_1);
        ptmt_1.setString(1, name);
        ResultSet rs=ptmt_1.executeQuery();
        if(!rs.next()){
            return false;
        }
        int id=rs.getInt("id");
        String sql_2=" DELETE FROM user "
                +" WHERE BINARY user_name=?";
        PreparedStatement ptmt_2=conn.prepareStatement(sql_2);
        ptmt_2.setString(1, name);
        ptmt_2.execute();
        String sql_3=" DELETE FROM friend "
                +" WHERE a_id=? OR b_id=?";
        PreparedStatement ptmt_3=conn.prepareStatement(sql_3);
        ptmt_3.setInt(1, id);
        ptmt_3.setInt(2, id);
        ptmt_3.execute();
        return true;
    }
    public boolean is_exis(String name) throws SQLException {
        Connection conn= DBUtil.getConnection();
        String sql=" SELECT 1 FROM user "
                +" WHERE BINARY user_name=?";
        PreparedStatement ptmt=conn.prepareStatement(sql);
        ptmt.setString(1, name);
        ResultSet rs=ptmt.executeQuery();
        if(!rs.next())
            return false;
        else
            return true;
    }
}
