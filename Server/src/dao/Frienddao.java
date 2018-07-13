package dao;

import db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSC333 on 2018/4/2.
 */
public class Frienddao {
    public boolean addFriend(String name_a, String name_b, String nickname) throws SQLException {
        Userdao userdao=new Userdao();
        if(userdao.is_exis(name_b)){
            Connection conn= DBUtil.getConnection();
            String sql_1=" SELECT id FROM user "
                    +" WHERE BINARY user_name=?";
            PreparedStatement ptmta=conn.prepareStatement(sql_1);
            ptmta.setString(1, name_a);
            ResultSet rsa=ptmta.executeQuery();
            int a_id=0;
            if(rsa.next()){
                a_id=rsa.getInt("id");
            }
            PreparedStatement ptmt_b=conn.prepareStatement(sql_1);
            ptmt_b.setString(1, name_b);
            ResultSet rs_b=ptmt_b.executeQuery();
            int b_id=0;
            if(rs_b.next()){
                b_id=rs_b.getInt("id");
            }
            String sql="SELECT 1 FROM friend "
                    +" WHERE a_id=? AND b_id=? ";
            PreparedStatement p=conn.prepareStatement(sql);
            p.setInt(1, a_id);
            p.setInt(2, b_id);
            ResultSet resultSet=p.executeQuery();
            if(resultSet.next()){
                return true;
            }
            String sql_2=" INSERT INTO friend "
                    +" VALUES (NULL, ?, ?, ?) ";
            PreparedStatement ptmt=conn.prepareStatement(sql_2);
            ptmt.setInt(1, a_id);
            ptmt.setInt(2, b_id);
            ptmt.setString(3, nickname);
            ptmt.execute();
            return true;
        }
        else
            return false;
    }
    public boolean delFriend(String name_a, String name_b) throws SQLException {
        Userdao userdao=new Userdao();
        Connection conn= DBUtil.getConnection();
        String sql_1=" SELECT id FROM user "
                +" WHERE user_name=? ";
        PreparedStatement ptmt_a=conn.prepareStatement(sql_1);
        ptmt_a.setString(1, name_a);
        ResultSet rs_a=ptmt_a.executeQuery();
        int a=0;
        if(rs_a.next()){
            a=rs_a.getInt("id");
        }
        String sql_2="DELETE FROM friend "
                +" WHERE a_id=? AND nickname=? ";
        PreparedStatement ptmt=conn.prepareStatement(sql_2);
        ptmt.setInt(1, a);
        ptmt.setString(2, name_b);
        ptmt.execute();
        return true;
    }
    public List<String> getFriendList(String name) throws SQLException {
        //访问数据库，获得好友列表数据
        Userdao userdao=new Userdao();
        List<String> FriendList=new ArrayList<String>();
        Connection conn= DBUtil.getConnection();
        String sql_1="SELECT id FROM user "
                +" WHERE BINARY user_name=? ";
        PreparedStatement ptmt_1=conn.prepareStatement(sql_1);
        ptmt_1.setString(1, name);
        ResultSet rs_1=ptmt_1.executeQuery();
        int id=0;
        if(rs_1.next()){
            id=rs_1.getInt("id");
        }
        String sql_2="SELECT nickname FROM friend "
                +" WHERE a_id=? ";
        PreparedStatement ptmt_2=conn.prepareStatement(sql_2);
        ptmt_2.setInt(1, id);
        ResultSet rs_2=ptmt_2.executeQuery();
        while(rs_2.next()){
            FriendList.add(rs_2.getString("nickname"));
        }
        return FriendList;
    }
}
