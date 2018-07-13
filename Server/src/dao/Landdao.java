package dao;

import db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LSC333 on 2018/4/3.
 */
public class Landdao {
    public String userLanding(String name, String password) throws SQLException {
        Connection conn= DBUtil.getConnection();
        String res="";
        boolean flag1=false;
        boolean flag2=false;
        String sql_1=" SELECT 1 FROM user "
                +" WHERE user_name=?";
        PreparedStatement ptmt_1=conn.prepareStatement(sql_1);
        ptmt_1.setString(1, name);
        ResultSet rs_1=ptmt_1.executeQuery();
        if(rs_1.next()){
            flag1=true;
        }
        String sql_2="SELECT 1 FROM user "
                +" WHERE user_name=? AND password=?";
        PreparedStatement ptmt_2=conn.prepareStatement(sql_2);
        ptmt_2.setString(1, name);
        ptmt_2.setString(2, password);
        ResultSet rs_2=ptmt_2.executeQuery();
        if(rs_2.next()){
            flag2=true;
        }
        if(flag1){
            if(flag2){
                return res;
            }else{
                res=res+"密码错误";
                return res;
            }
        }else{
            res=res+"用户不存在";
            return res;
        }
    }
}
